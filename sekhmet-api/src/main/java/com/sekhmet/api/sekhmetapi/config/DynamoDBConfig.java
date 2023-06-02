package com.sekhmet.api.sekhmetapi.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.sekhmet.api.sekhmetapi.repository.UserRepository;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableDynamoDBRepositories(basePackageClasses = UserRepository.class)
public class DynamoDBConfig {

    @Value("${amazon.aws.region}")
    private String region;

    @Value("${amazon.dynamodb.endpoint}")
    private String amazonDynamoDBEndpoint;
    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        var endpointConfiguration = new AwsClientBuilder.EndpointConfiguration(amazonDynamoDBEndpoint, region);

        return AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(endpointConfiguration)
                .build();
    }
}
