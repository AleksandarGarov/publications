resource "aws_s3_bucket" "this" {
  bucket = "publications-static-repository"
  acl    = "private"

  force_destroy = true

  versioning {
    enabled = false
  }

  server_side_encryption_configuration {
    rule {
      apply_server_side_encryption_by_default {
        sse_algorithm = "AES256"
      }
    }
  }
}

resource "aws_s3_bucket_public_access_block" "this" {
  bucket = aws_s3_bucket.this.id

  block_public_acls       = true
  block_public_policy     = true
  ignore_public_acls      = true
  restrict_public_buckets = true
}

data "aws_iam_policy_document" "this" {
  statement {
    actions = ["s3:*"]
    principals {
      type        = "AWS"
      identifiers = ["*"]
    }
    resources = ["${aws_s3_bucket.this.arn}/*"]

    effect = "Deny"

    condition {
      test     = "Bool"
      values   = ["false"]
      variable = "aws:SecureTransport"
    }
  }

  statement {
    sid    = "1"
    effect = "Allow"
    actions = [
      "s3:GetObject"
    ]
    principals {
      type        = "AWS"
      identifiers = ["arn:aws:iam::cloudfront:user/CloudFront Origin Access Identity ${var.origin_access_identity_id}"]
    }
    resources = [
      "${aws_s3_bucket.this.arn}/*"
    ]
  }
}

resource "aws_s3_bucket_policy" "this" {
  depends_on = [aws_s3_bucket_public_access_block.this]
  bucket     = aws_s3_bucket.this.id
  policy     = data.aws_iam_policy_document.this.json
}

resource "aws_s3_bucket_object" "this" {
  for_each     = fileset("${local.static_files_path}/", "**")
  bucket       = aws_s3_bucket.this.bucket
  key          = each.value
  source       = "${local.static_files_path}/${each.value}"
  etag         = filemd5("${local.static_files_path}/${each.value}")
  content_type = length(regexall("^.*\\.(.*)", each.value)) > 0 ? lookup(local.extension_to_mime, element(regex("^.*\\.(.*)", each.value), 0), null) : null
}