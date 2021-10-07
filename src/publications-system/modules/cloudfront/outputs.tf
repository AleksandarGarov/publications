output "distribution_id" {
  value = aws_cloudfront_distribution.this.id
}

output "distribution_arn" {
  value = aws_cloudfront_distribution.this.arn
}

output "origin_access_identity_id" {
  value = aws_cloudfront_origin_access_identity.origin_access_identity.id
}