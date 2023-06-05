
resource "aws_security_group" "launch-template-sg" {
  name        = "Sekhmet-api-Dev-lt-sg"
  description = "Security group for ALB"

  ingress {
    from_port   = 8080
    to_port     = 8080
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
resource "aws_launch_template" "launch_template" {
  name                   = "Sekhmet-api-Dev-lt"
  image_id               = var.ami_id
  instance_type          = var.instance_type
  vpc_security_group_ids = [aws_security_group.launch-template-sg.id]
  user_data              = var.user_data
  instance_initiated_shutdown_behavior = "terminate"

  tag_specifications {
    resource_type = "instance"
    tags = {
      Name = "Sekhmet-api-Dev"
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


resource "aws_autoscaling_policy" "scale_out_policy" {
  name                   = "ScaleOut"
  autoscaling_group_name = aws_autoscaling_group.asg.name
  adjustment_type        = "ChangeInCapacity"
  cooldown               = 300
  scaling_adjustment     = 1

  target_tracking_configuration {
    predefined_metric_specification {
      predefined_metric_type = "ASGAverageCPUUtilization"
    }

    target_value = var.scale_out_cpu
  }
}

resource "aws_autoscaling_policy" "scale_in_policy" {
  name                   = "ScaleIn"
  autoscaling_group_name = aws_autoscaling_group.asg.name
  adjustment_type        = "ChangeInCapacity"
  cooldown               = 300
  scaling_adjustment     = -1

  target_tracking_configuration {
    predefined_metric_specification {
      predefined_metric_type = "ASGAverageCPUUtilization"
    }

    target_value = var.scale_in_cpu
  }
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
