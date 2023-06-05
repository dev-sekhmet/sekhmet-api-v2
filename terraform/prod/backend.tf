terraform {
  backend "s3" {
    bucket         = "sekhmet-terraform-state"
    key            = "prod/terraform.tfstate"
    region         = "eu-west-3"
    encrypt        = true
    dynamodb_table = "Prod-terraform-lock"
  }
}
