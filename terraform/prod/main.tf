# Create Application Load Balancer
module "alb" {
  source          = "./modules/alb"
  vpc_id          = var.vpc_id
  alb_name        = var.alb_name
  target_port     = var.target_port
  listener_port   = var.listener_port
  security_groups = var.alb_security_groups
}

# Create Auto Scaling Group
module "asg" {
  source           = "./modules/asg"
  vpc_id           = var.vpc_id
  asg_name         = var.asg_name
  min_instances    = var.min_instances
  max_instances    = var.max_instances
  desired_capacity = var.desired_capacity
  target_group_arn = module.alb.target_group_arn
  scale_out_cpu    = var.scale_out_cpu
  scale_in_cpu     = var.scale_in_cpu
  ami_id           = var.custom_ami_id
  user_data_script = var.user_data_script
}

# Create DynamoDB table
module "dynamodb" {
  source               = "./modules/dynamodb"
  table_name_prefix    = var.table_name_prefix
  capacity_mode        = var.dynamodb_capacity_mode
  read_capacity_units  = var.dynamodb_read_capacity_units
  write_capacity_units = var.dynamodb_write_capacity_units
  environment          = "Prod"
}

# Route53 CNAME record
resource "aws_route53_record" "cname" {
  zone_id = var.route53_zone_id
  name    = var.route53_cname_name
  type    = "CNAME"
  ttl     = 300
  records = [module.alb.alb_dns_name] # Update with prod ALB DNS name
}
