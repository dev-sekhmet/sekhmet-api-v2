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
  default = "IyEvYmluL2Jhc2gKIyEvYmluL2Jhc2gKCiMgR2V0IHRoZSBDb2RlQXJ0aWZhY3QgYXV0aG9yaXph dGlvbiB0b2tlbgpBVVRIX1RPS0VOPSQoYXdzIGNvZGVhcnRpZmFjdCBnZXQtYXV0aG9yaXphdGlv bi10b2tlbiAtLWRvbWFpbiBzZWtobWV0LWJhY2tlbmRzIC0tZG9tYWluLW93bmVyICR7dmFyLmRv bWFpbl9vd25lcn0gLS1yZWdpb24gZXUtd2VzdC0zIC0tcXVlcnkgYXV0aG9yaXphdGlvblRva2Vu IC0tb3V0cHV0IHRleHQpCgojIFVwZGF0ZSB0aGUgc2V0dGluZ3MueG1sIGZpbGUgd2l0aCB0aGUg YXV0aG9yaXphdGlvbiB0b2tlbiBhbmQgdmVyc2lvbgpjYXQgPDxFT0YgPiAvaG9tZS9lYzItdXNl ci8ubTIvc2V0dGluZ3MueG1sCjxzZXR0aW5ncz4KICA8c2VydmVycz4KICAgIDxzZXJ2ZXI+CiAg ICAgIDxpZD5zZWtobWV0LWJhY2tlbmRzPC9pZD4KICAgICAgPHVzZXJuYW1lPmF3czwvdXNlcm5h bWU+CiAgICAgIDxwYXNzd29yZD4ke0FVVEhfVE9LRU59PC9wYXNzd29yZD4KICAgIDwvc2VydmVy PgogIDwvc2VydmVycz4KICAgIDxwcm9maWxlcz4KICAgICAgICA8cHJvZmlsZT4KICAgICAgICAg ICAgPGlkPmRlZmF1bHQ8L2lkPgogICAgICAgICAgICA8cmVwb3NpdG9yaWVzPgogICAgICAgICAg ICAgICAgPHJlcG9zaXRvcnk+CiAgICAgICAgICAgICAgICAgICAgPGlkPnNla2htZXQtYmFja2Vu ZHM8L2lkPgogICAgICAgICAgICAgICAgICAgIDx1cmw+JHt2YXIuY29kZWFydGlmYWN0X2RvbWFp bl9uYW1lfTwvdXJsPgogICAgICAgICAgICAgICAgPC9yZXBvc2l0b3J5PgogICAgICAgICAgICA8 L3JlcG9zaXRvcmllcz4KICAgICAgICA8L3Byb2ZpbGU+CiAgICA8L3Byb2ZpbGVzPgogICAgPGFj dGl2ZVByb2ZpbGVzPgogICAgICAgIDxhY3RpdmVQcm9maWxlPmRlZmF1bHQ8L2FjdGl2ZVByb2Zp bGU+CiAgICA8L2FjdGl2ZVByb2ZpbGVzPgo8L3NldHRpbmdzPgpFT0YKCiMgRG93bmxvYWQgdGhl IEpBUiBmaWxlIGZyb20gQ29kZUFydGlmYWN0Cm12biBkZXBlbmRlbmN5OmdldCAtRGdyb3VwSWQ9 Y29tLnNla2htZXQuYXBpIC1EYXJ0aWZhY3RJZD1zZWtobWV0LWFwaSAtRHZlcnNpb249XCR7c2Vr aG1ldC52ZXJzaW9ufSAtRHRyYW5zaXRpdmU9ZmFsc2UKCiMgQ3JlYXRlIHRoZSBlbnZpcm9ubWVu dCB2YXJpYWJsZXMgZmlsZQpjYXQgPDxFT0YgPiAvaG9tZS9lYzItdXNlci9zcHJpbmctYm9vdC1o b21lL2Vudi5jb25mCkFQUExJQ0FUSU9OX0VOVj0ke3Zhci5hcHBsaWNhdGlvbl9lbnZ9CkFQUExJ Q0FUSU9OX1RXSUxJT19BQ0NPVU5UX1NJRD0ke3Zhci50d2lsaW9fYWNjb3VudF9zaWR9CkFQUExJ Q0FUSU9OX1RXSUxJT19BUElfU0VDUkVUPSR7dmFyLnR3aWxpb19hcGlfc2VjcmV0fQpBUFBMSUNB VElPTl9UV0lMSU9fQVVUSF9UT0tFTj0ke3Zhci50d2lsaW9fYXV0aF90b2tlbn0KQVBQTElDQVRJ T05fVFdJTElPX1ZFUklGWV9TSUQ9JHt2YXIudHdpbGlvX3ZlcmlmeV9zaWR9CkFNQVpPTl9EWU5B TU9EQl9FTkRQT0lOVD0ke3Zhci5keW5hbW9kYl9lbmRwb2ludH0KQVBQTElDQVRJT05fVFdJTElP X0NPTlZFUlNBVElPTl9TSUQ9JHt2YXIudHdpbGlvX2NvbnZlcnNhdGlvbl9zaWR9CkVPRgoKIyBM aW5rIHRoZSBKQVIgZmlsZQpsbiAtc2YgL2hvbWUvZWMyLXVzZXIvLm0yL3JlcG9zaXRvcnkvY29t L3Nla2htZXQvYXBpL3Nla2htZXQtYXBpLyR7dmFyLmFwcGxpY2F0aW9uX3ZlcnNpb259L3Nla2ht ZXQtYXBpLSR7dmFyLmFwcGxpY2F0aW9uX3ZlcnNpb259LmphciAvaG9tZS9lYzItdXNlci9zcHJp bmctYm9vdC1ob21lL2FwcC5qYXIKCiMgUmVzdGFydCB0aGUgYXBwIHNlcnZpY2UKc3VkbyBzeXN0 ZW1jdGwgcmVzdGFydCBhcHAuc2VydmljZQ=="
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
  default     = "Sekhmet-api-Dev-ALB-TargetGroup"
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
