terraform {
  backend "s3" {
    bucket         = "sekhmet-terraform-state"
    key            = "dev/terraform.tfstate"
    region         = "eu-west-3"
    encrypt        = true
    dynamodb_table = "Dev-terraform-lock"
  }
}
