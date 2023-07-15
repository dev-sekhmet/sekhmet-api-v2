# Create Application Load Balancer
module "alb" {
  source          = "../modules/alb"
  vpc_id          = var.vpc_id
  alb_name        = var.alb_name
  target_port     = var.target_port
  listener_port   = var.listener_port
  security_groups = var.alb_security_groups
  subnets         = var.subnets
  target_group_name = var.target_group_name
  application_env = "Prod"
}

# Create Auto Scaling Group
module "asg" {
  source           = "../modules/asg"
  vpc_id           = var.vpc_id
  asg_name         = var.asg_name
  min_instances    = var.min_instances
  max_instances    = var.max_instances
  desired_capacity = var.desired_capacity
  target_group_arn = module.alb.target_group_arn
  scale_out_cpu    = var.scale_out_cpu
  scale_in_cpu     = var.scale_in_cpu
  ami_id           = var.ami_id
  user_data = var.user_data
  codeartefact_domain_name = var.codeartefact_domain_name
  codeartefact_domain_owner = var.codeartefact_domain_owner
  instance_profile = var.instance_profile
  instance_type = var.instance_type
  route53_cname_name = var.route53_cname_name
  route53_zone_id = var.route53_zone_id
  subnets = var.subnets
  key_name = var.key_name
  application_version = var.application_version
  application_env = "Prod"
  twilio_conversation_sid = var.twilio_conversation_sid
  twilio_account_sid = var.twilio_account_sid
  twilio_auth_token = var.twilio_auth_token
  twilio_api_secret = var.twilio_api_secret
  twilio_verify_sid = var.twilio_verify_sid
  dynamodb_endpoint = var.dynamodb_endpoint
  aws_region = var.aws_region
}

# Create DynamoDB table
module "dynamodb" {
  source               = "../modules/dynamodb"
  dynamodb_capacity_mode        = var.dynamodb_capacity_mode
  dynamodb_read_capacity_units  = var.dynamodb_read_capacity_units
  dynamodb_write_capacity_units = var.dynamodb_write_capacity_units
  application_env = "Prod"
}

# Route53 CNAME record
/*resource "aws_route53_record" "cname" {
  zone_id = var.route53_zone_id
  name    = var.route53_cname_name
  type    = "CNAME"
  ttl     = 300
  records = [module.alb.alb_dns_name] # Update with prod ALB DNS name
}*/
