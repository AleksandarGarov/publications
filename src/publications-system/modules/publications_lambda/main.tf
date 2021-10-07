data "aws_region" "current" {}
data "aws_caller_identity" "current" {}

resource "aws_lambda_function" "this" {
  s3_bucket     = var.lambda_repository_bucket
  s3_key        = var.lambda_object_key
  function_name = var.lambda_function_name
  runtime       = "java11"
  timeout       = var.lambda_timeout
  memory_size   = var.lambda_memory
  handler       = "org.alga.handlers.PublicationsLambdaHandler"
  role          = aws_iam_role.this.arn
  depends_on    = [aws_cloudwatch_log_group.this]
  publish       = var.publish
  environment {
    variables = {
      WORKSPACE = terraform.workspace
    }
  }

  tracing_config {
    mode = "Active"
  }
}

resource "aws_cloudwatch_log_group" "this" {
  name = "/aws/lambda/${var.lambda_function_name}"
}
