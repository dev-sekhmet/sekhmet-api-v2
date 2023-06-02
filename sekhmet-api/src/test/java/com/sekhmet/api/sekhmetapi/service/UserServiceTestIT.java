package com.sekhmet.api.sekhmetapi.service;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.sekhmet.api.sekhmetapi.domain.User;
import com.sekhmet.api.sekhmetapi.repository.UserRepository;
import com.sekhmet.api.sekhmetapi.service.dto.sms.CheckPhoneVerificationRequest;
import com.sekhmet.api.sekhmetapi.service.impl.UserServiceImpl;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testcontainers.containers.localstack.LocalStackContainer.Service.DYNAMODB;

@SpringBootTest
@Testcontainers
public class UserServiceTestIT {

    @Container
    public static LocalStackContainer localStack = new LocalStackContainer(DockerImageName.parse("localstack/localstack:0.12.10"))
            .withServices(DYNAMODB);

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserRepository userRepository;

    private static String tableNamePrefix;

    @Value("${amazon.dynamodb.table-name-prefix}")
    public void setTableNamePrefix(String tableNamePrefix){
        UserServiceTestIT.tableNamePrefix = tableNamePrefix;
    }
    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("amazon.dynamodb.endpoint", () -> localStack.getEndpointOverride(DYNAMODB).toString());
    }

    @BeforeAll
    public static void setUp() {
        var amazonDynamoDB = AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder
                        .EndpointConfiguration(localStack.getEndpointOverride(DYNAMODB).toString(), localStack.getRegion()))
                .build();
        var dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);

        // Create table
        DynamoDBMapperConfig.Builder builder = new
                DynamoDBMapperConfig.Builder();
        builder.setTableNameOverride(DynamoDBMapperConfig.TableNameOverride.withTableNamePrefix("dev"));
        var tableRequest = dynamoDBMapper.generateCreateTableRequest(User.class, builder.build());
        tableRequest.setProvisionedThroughput(new ProvisionedThroughput(5L, 5L));
        amazonDynamoDB.createTable(tableRequest);
    }

    @AfterAll
    public static void tearDown() {
        // Cleanup tables
        // ...
    }

    @Test
    public void testRegisterUserByPhoneNumber() {
        // Given
        var request = new CheckPhoneVerificationRequest("1234567890", "238765", "fr", "fr");
        var expectedUser = new User();
        expectedUser.setPhoneNumber(request.getPhoneNumber());

        // When
        var result = userService.registerUserByPhoneNumber(request);
        result.setId(null);

        // Then
        assertThat(result).isEqualTo(expectedUser);
    }

    // ...

    @Test
    public void testGetUserByPhoneNumber() {
        // Given
        String phoneNumber = "1234567891";
        User expectedUser = new User();
        expectedUser.setId(UUID.randomUUID());
        expectedUser.setPhoneNumber(phoneNumber);
        userRepository.save(expectedUser); // Assuming UserRepository has a save method

        // When
        Optional<User> result = userService.getUserByPhoneNumber(phoneNumber);

        // Then
        assertThat(result).isPresent().get().isEqualTo(expectedUser);
    }
}

