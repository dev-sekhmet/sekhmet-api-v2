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

  metric {
    name = "CPUUtilization"
    unit = "Percent"
  }

  scaling_policy {
    name                    = "ScaleOut"
    adjustment_type         = "ChangeInCapacity"
    cooldown                = 300
    scaling_adjustment      = 1
    metric_aggregation_type = "Average"

    target_tracking_configuration {
      predefined_metric_specification {
        predefined_metric_type = "ASGAverageCPUUtilization"
      }

      target_value = var.scale_out_cpu
    }
  }

  scaling_policy {
    name                    = "ScaleIn"
    adjustment_type         = "ChangeInCapacity"
    cooldown                = 300
    scaling_adjustment      = -1
    metric_aggregation_type = "Average"

    target_tracking_configuration {
      predefined_metric_specification {
        predefined_metric_type = "ASGAverageCPUUtilization"
      }

      target_value = var.scale_in_cpu
    }
  }
}
