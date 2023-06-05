resource "aws_dynamodb_table" "dynamodb_table" {
  name             = "Dev_User"
  billing_mode     = var.dynamodb_capacity_mode
  read_capacity    = var.dynamodb_read_capacity_units
  write_capacity   = var.dynamodb_write_capacity_units
  hash_key       = "id"
  attribute {
    name = "id"
    type = "S"
  }
}
