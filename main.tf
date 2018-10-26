provider "aws" {
  assume_role {
    role_arn = "arn:aws:iam::270507745549:role/terraform-deploy"
  }
  region = "us-east-2"
}

resource "aws_security_group" "terraform-test" {
  name        = "terraform-test"
  description = "Allow all inbound traffic"

  ingress {
    from_port   = 0
    to_port     = 65535
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags {
    Name = "allow_all"
  } 
}
