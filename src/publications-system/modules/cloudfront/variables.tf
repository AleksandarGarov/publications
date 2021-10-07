variable "api_gateway_endpoint" {
  description = "The endpoint for the api gateway"
}

variable "api_gateway_path_pattern" {
  description = "The api gateway path pattern"
}

variable "static_files_bucket_domain_name" {
  description = "The domain name of the static files bucket"
  type        = string
}

variable "static_files_bucket_name" {
  description = "The name of the static files bucket"
  type        = string
}