output "base_url" {
  value = aws_api_gateway_deployment.this.invoke_url
}

output "api_endpoint" {
  value = replace(aws_api_gateway_deployment.this.invoke_url, "/v1", "")
}
