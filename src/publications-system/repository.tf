module "artifact_repository" {
  source = "./modules/repository_s3_bucket"
  name   = "publications-artifact-repository"
}

output "artifact_repository_bucket_name" {
  value = module.artifact_repository.bucket_name
}

output "artifact_repository_bucket_id" {
  value = module.artifact_repository.bucket_id
}