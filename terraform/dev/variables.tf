variable "vpc_id" {
  description = "ID of the VPC"
  type        = string
}

variable "alb_name" {
  description = "Name of the Application Load Balancer"
  type        = string
}

variable "security_groups" {
  description = "List of security group IDs for the instances"
  type        = list(string)
  default     = []
}

variable "alb_security_groups" {
  description = "List of security group IDs for the ALB"
  type        = list(string)
  default     = []
}

variable "subnets" {
  description = "List of subnet IDs for the instances"
  type        = list(string)
  default     = []
}

variable "asg_name" {
  description = "Name of the Auto Scaling Group"
  type        = string
}

variable "min_instances" {
  description = "Minimum number of instances in the Auto Scaling Group"
  type        = number
  default     = 1
}

variable "max_instances" {
  description = "Maximum number of instances in the Auto Scaling Group"
  type        = number
  default     = 6
}

variable "desired_capacity" {
  description = "Desired capacity of the Auto Scaling Group"
  type        = number
  default     = 1
}

variable "scale_out_cpu" {
  description = "CPU utilization threshold for scale out"
  type        = number
  default     = 85
}

variable "scale_in_cpu" {
  description = "CPU utilization threshold for scale in"
  type        = number
  default     = 30
}

variable "ami_id" {
  description = "ID of the custom AMI"
  type        = string
}

variable "user_data" {
  description = "User data script to be executed on the instances"
  type        = string
  default = "../user-data/user-data.sh"
}

variable "table_name_prefix" {
  description = "Prefix for the DynamoDB table name"
  type        = string
}

variable "dynamodb_capacity_mode" {
  description = "DynamoDB capacity mode (PROVISIONED or PAY_PER_REQUEST)"
  type        = string
  default     = "PROVISIONED"
}
variable "instance_type" {
  description = "Instance type for the instances"
  type        = string
}

variable "dynamodb_read_capacity_units" {
  description = "Provisioned read capacity units for DynamoDB"
  type        = number
  default     = 10
}

variable "dynamodb_write_capacity_units" {
  description = "Provisioned write capacity units for DynamoDB"
  type        = number
  default     = 10
}

variable "route53_zone_id" {
  description = "ID of the Route 53 hosted zone"
  type        = string
}

variable "route53_cname_name" {
  description = "Name for the Route 53 CNAME record"
  type        = string
}
variable "target_port" {
  description = "The target port for the ALB target group"
  type        = number
  default     = 8080
}

variable "listener_port" {
  description = "The listener port for the ALB"
  type        = number
  default     = 80
}
variable "target_group_name" {
  description = "The name of the ALB target group"
  type        = string
  default     = "my-target-group"
}

variable "region" {
  description = "The region"
  type        = string
  default     = "eu-west-3"
}
variable "instance_profile" {
  description = "The instance profile"
  type        = string
}
variable "codeartefact_domain_owner" {
  description = "The domain owner"
  type        = string
}
variable "codeartefact_domain_name" {
  description = "The domain name"
  type        = string
}


# application variables
variable "application_version" {
  type    = string
  default = "0.0.2-SNAPSHOT"
}

variable "application_env" {
  type    = string
  default = "Dev"
}

variable "twilio_account_sid" {
  type    = string
  default = "your_twilio_account_sid"
}

variable "twilio_api_secret" {
  type    = string
  default = "your_twilio_api_secret"
}

variable "twilio_auth_token" {
  type    = string
  default = "your_twilio_auth_token"
}

variable "twilio_verify_sid" {
  type    = string
  default = "your_twilio_verify_sid"
}

variable "dynamodb_endpoint" {
  type    = string
  default = "dynamodb.eu-west-3.amazonaws.com"
}

variable "twilio_conversation_sid" {
  type    = string
  default = "your_twilio_conversation_sid"
}
