
resource "aws_security_group" "launch-template-sg" {
  name        = "Sekhmet-api-${var.application_env}-lt-sg"
  description = "Security group for ALB"

  ingress {
    from_port   = 8080
    to_port     = 8080
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 22  # SSH port
    to_port     = 22  # SSH port
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }


  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "sekhmet-alb-security-group"
  }
}

data "template_file" "userdata" {
  template = file("${path.cwd}/../user-data/user-data.sh.tpl")

  vars = {
    codeartefact_domain_owner = var.codeartefact_domain_owner
    codeartefact_domain_name = var.codeartefact_domain_name
    application_version = var.application_version
    application_env = var.application_env
    twilio_account_sid = var.twilio_account_sid
    twilio_api_secret = var.twilio_api_secret
    twilio_auth_token = var.twilio_auth_token
    twilio_verify_sid = var.twilio_verify_sid
    dynamodb_endpoint = var.dynamodb_endpoint
    aws_region = var.aws_region
    twilio_conversation_sid = var.twilio_conversation_sid
  }
}

resource "aws_launch_template" "launch_template" {
  name                   = "Sekhmet-api-${var.application_env}-lt"
  image_id               = var.ami_id
  instance_type          = var.instance_type
  vpc_security_group_ids = [aws_security_group.launch-template-sg.id]
  user_data              = "${base64encode(data.template_file.userdata.rendered)}"
  instance_initiated_shutdown_behavior = "terminate"
  key_name               = var.key_name

  tag_specifications {
    resource_type = "instance"
    tags = {
      Name = "Sekhmet-api-${var.application_env}"
    }
  }
  iam_instance_profile {
    name = var.instance_profile
  }

  metadata_options {
    http_endpoint               = "enabled"
    http_tokens                 = "optional"
    http_put_response_hop_limit = 1
    instance_metadata_tags      = "enabled"
  }
}

resource "aws_cloudwatch_metric_alarm" "scale_in_alarm" {
  alarm_name          = "Sekhmet-api-${var.application_env}-ScaleInAlarm"
  comparison_operator = "LessThanOrEqualToThreshold"
  evaluation_periods  = 1
  metric_name         = "CPUUtilization"
  namespace           = "AWS/EC2"
  period              = 60
  statistic           = "Average"
  threshold           = var.scale_in_cpu
  alarm_description   = "Scale in when CPU utilization is below scale_in_cpu%"
  alarm_actions       = [aws_autoscaling_policy.scale_in_policy.arn]
  dimensions = {
    AutoScalingGroupName = aws_autoscaling_group.asg.name
  }
}

resource "aws_cloudwatch_metric_alarm" "scale_out_alarm" {
  alarm_name          = "Sekhmet-api-${var.application_env}-ScaleOutAlarm"
  comparison_operator = "GreaterThanOrEqualToThreshold"
  evaluation_periods  = 1
  metric_name         = "CPUUtilization"
  namespace           = "AWS/EC2"
  period              = 60
  statistic           = "Average"
  threshold           = var.scale_out_cpu
  alarm_description   = "Scale out when CPU utilization is above scale_out_cpu%"
  alarm_actions       = [aws_autoscaling_policy.scale_out_policy.arn]
  dimensions = {
    AutoScalingGroupName = aws_autoscaling_group.asg.name
  }
}

resource "aws_autoscaling_policy" "scale_out_policy" {
  name                   = "Sekhmet-api-${var.application_env}-ScaleOutPolicy"
  autoscaling_group_name = aws_autoscaling_group.asg.name
  policy_type            = "SimpleScaling"
  cooldown               = 300
  adjustment_type        = "ChangeInCapacity"
  scaling_adjustment     = 1
}

resource "aws_autoscaling_policy" "scale_in_policy" {
  name                   = "Sekhmet-api-${var.application_env}-ScaleInPolicy"
  autoscaling_group_name = aws_autoscaling_group.asg.name
  policy_type            = "SimpleScaling"
  cooldown               = 300
  adjustment_type        = "ChangeInCapacity"
  scaling_adjustment     = -1
}




resource "aws_autoscaling_group" "asg" {
  name                      = var.asg_name
  min_size                  = var.min_instances
  max_size                  = var.max_instances
  desired_capacity          = var.desired_capacity
  vpc_zone_identifier       = var.subnets
  target_group_arns         = [var.target_group_arn]
  health_check_type         = "ELB"
  health_check_grace_period = 300
  termination_policies      = ["OldestInstance"]

  tag {
    key                 = "Name"
    value               = var.asg_name
    propagate_at_launch = true
  }

  lifecycle {
    ignore_changes = [desired_capacity]
  }
  launch_template {
    id      = aws_launch_template.launch_template.id
    version = "$Latest"
  }

  depends_on = [aws_launch_template.launch_template]
}
