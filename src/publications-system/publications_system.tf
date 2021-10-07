locals {
  identifier_prefix = "publications-artifact-repository"
  lambda_source     = abspath("${path.module}/../../src/publications-system/lambdas/publications/build/distributions/publications.zip")
  lambda_hash       = filemd5(local.lambda_source)
  lambda_key        = "lambda/lambda-${local.lambda_hash}.zip"
}

resource "aws_s3_bucket_object" "lambda" {
  bucket = module.artifact_repository.bucket_id
  key    = local.lambda_key
  source = local.lambda_source
  etag   = local.lambda_hash
}

module "publications_lambda" {
  source                   = "./modules/publications_lambda"
  lambda_function_name     = "publications-lambda"
  lambda_memory            = "1024"
  lambda_object_key        = aws_s3_bucket_object.lambda.key
  lambda_repository_bucket = module.artifact_repository.bucket_id
  lambda_timeout           = 60
}

module "static_repository" {
  source = "./modules/static_s3_bucket"
  origin_access_identity_id = module.cloudfront.origin_access_identity_id
}

module "cloudfront" {
  source = "./modules/cloudfront"
  api_gateway_endpoint = module.publications_lambda.api_endpoint
  api_gateway_path_pattern = "/v1/*"
  static_files_bucket_domain_name = module.static_repository.static_files_bucket_domain_name
  static_files_bucket_name = module.static_repository.static_files_bucket_name
}

module "database" {
  source     = "./modules/database"
  identifier = "mysqlpublications"
  name       = "publications"
  password   = "rootpubroot"
  username   = "root"
}