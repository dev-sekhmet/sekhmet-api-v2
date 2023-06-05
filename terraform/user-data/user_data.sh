#!/bin/bash
#!/bin/bash

# Get the CodeArtifact authorization token
AUTH_TOKEN=$(aws codeartifact get-authorization-token --domain sekhmet-backends --domain-owner ${var.domain_owner} --region eu-west-3 --query authorizationToken --output text)

# Update the settings.xml file with the authorization token and version
cat <<EOF > /home/ec2-user/.m2/settings.xml
<settings>
  <servers>
    <server>
      <id>sekhmet-backends</id>
      <username>aws</username>
      <password>${AUTH_TOKEN}</password>
    </server>
  </servers>
    <profiles>
        <profile>
            <id>default</id>
            <repositories>
                <repository>
                    <id>sekhmet-backends</id>
                    <url>${var.codeartifact_domain_name}</url>
                </repository>
            </repositories>
        </profile>
    </profiles>
    <activeProfiles>
        <activeProfile>default</activeProfile>
    </activeProfiles>
</settings>
EOF

# Download the JAR file from CodeArtifact
mvn dependency:get -DgroupId=com.sekhmet.api -DartifactId=sekhmet-api -Dversion=\${sekhmet.version} -Dtransitive=false

# Create the environment variables file
cat <<EOF > /home/ec2-user/spring-boot-home/env.conf
APPLICATION_ENV=${var.application_env}
APPLICATION_TWILIO_ACCOUNT_SID=${var.twilio_account_sid}
APPLICATION_TWILIO_API_SECRET=${var.twilio_api_secret}
APPLICATION_TWILIO_AUTH_TOKEN=${var.twilio_auth_token}
APPLICATION_TWILIO_VERIFY_SID=${var.twilio_verify_sid}
AMAZON_DYNAMODB_ENDPOINT=${var.dynamodb_endpoint}
APPLICATION_TWILIO_CONVERSATION_SID=${var.twilio_conversation_sid}
EOF

# Link the JAR file
ln -sf /home/ec2-user/.m2/repository/com/sekhmet/api/sekhmet-api/${var.application_version}/sekhmet-api-${var.application_version}.jar /home/ec2-user/spring-boot-home/app.jar

# Restart the app service
sudo systemctl restart app.service
