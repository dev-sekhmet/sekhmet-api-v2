

variable "dynamodb_read_capacity_units" {
  description = "Provisioned read capacity units for DynamoDB"
  type        = number
  default     = 5
}

variable "dynamodb_write_capacity_units" {
  description = "Provisioned write capacity units for DynamoDB"
  type        = number
  default     = 5
}

variable "dynamodb_capacity_mode" {
  description = "DynamoDB capacity mode (PROVISIONED or PAY_PER_REQUEST)"
  type        = string
  default     = "PROVISIONED"
}

variable "application_env" {
  type    = string
}
