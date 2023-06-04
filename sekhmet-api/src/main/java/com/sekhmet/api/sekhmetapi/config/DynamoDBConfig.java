package com.sekhmet.api.sekhmetapi.config;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverterFactory;
import com.sekhmet.api.sekhmetapi.repository.UserRepository;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableDynamoDBRepositories(dynamoDBMapperConfigRef = "dynamoDBMapperConfig", basePackageClasses = UserRepository.class)
public class DynamoDBConfig {

    @Value("${amazon.aws.region}")
    private String region;

    @Value("${amazon.dynamodb.endpoint}")
    private String amazonDynamoDBEndpoint;

    @Value("${amazon.dynamodb.table-name-prefix}")
    private String tableNamePrefix;

    @Bean
    public DynamoDBMapperConfig dynamoDBMapperConfig() {
        DynamoDBMapperConfig.Builder builder = new DynamoDBMapperConfig.Builder();
        builder.setTableNameOverride(tableNameOverrider());
        builder.setTypeConverterFactory(DynamoDBTypeConverterFactory.standard());
        return builder.build();
    }


    private DynamoDBMapperConfig.TableNameOverride tableNameOverrider() {
        return DynamoDBMapperConfig.TableNameOverride.withTableNamePrefix(tableNamePrefix + "_");
    }

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        var endpointConfiguration = new AwsClientBuilder.EndpointConfiguration(amazonDynamoDBEndpoint, region);

        return AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(endpointConfiguration)
                .build();
    }
}
