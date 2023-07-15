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
}

variable "application_env" {
  type    = string
}
