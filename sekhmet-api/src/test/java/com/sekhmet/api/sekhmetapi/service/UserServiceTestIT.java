package com.sekhmet.api.sekhmetapi.service;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.model.AmazonDynamoDBException;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.sekhmet.api.sekhmetapi.domain.User;
import com.sekhmet.api.sekhmetapi.repository.UserRepository;
import com.sekhmet.api.sekhmetapi.service.dto.sms.CheckPhoneVerificationRequest;
import com.sekhmet.api.sekhmetapi.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
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
@Slf4j
public class UserServiceTestIT {

    @Container
    public static LocalStackContainer localStack = new LocalStackContainer(DockerImageName.parse("localstack/localstack:0.12.10"))
            .withServices(DYNAMODB);

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserRepository userRepository;

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("amazon.dynamodb.endpoint", () -> localStack.getEndpointOverride(DYNAMODB).toString());
    }

    @BeforeAll
    public static void setUp() throws InterruptedException {
        var amazonDynamoDB = AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder
                        .EndpointConfiguration(localStack.getEndpointOverride(DYNAMODB).toString(), localStack.getRegion()))
                .build();
        var dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);

        // Create table
        DynamoDBMapperConfig.Builder builder = new
                DynamoDBMapperConfig.Builder();
        builder.setTableNameOverride(DynamoDBMapperConfig.TableNameOverride.withTableNamePrefix("dev_"));
        var tableRequest = dynamoDBMapper.generateCreateTableRequest(User.class, builder.build());
        tableRequest.setProvisionedThroughput(new ProvisionedThroughput(5L, 5L));
        CreateTableResult createTableResult = amazonDynamoDB.createTable(tableRequest);

        // Wait until the table is active
        TableUtils.waitUntilActive(amazonDynamoDB, createTableResult.getTableDescription().getTableName());
        // Now check if the table has been created
        try {
            var tableDescription = amazonDynamoDB.describeTable("dev_User").getTable();
            log.info("Table description: {}" , tableDescription);
        } catch (AmazonDynamoDBException e) {
            log.error("Failed to create table.", e);
        }

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


    @Test
    public void testGetUserByPhoneNumber() {
        // Given
        String phoneNumber = "1234567891";
        User expectedUser = new User();
        expectedUser.setId(UUID.randomUUID());
        expectedUser.setPhoneNumber(phoneNumber);
        userRepository.save(expectedUser); // Assuming UserRepository has a save method

        // When
        var result = userService.getUserByPhoneNumber(phoneNumber);

        // Then
        assertThat(result).isPresent().get().isEqualTo(expectedUser);
    }

    @Test
    @WithMockUser(username = "550e8400-e29b-41d4-a716-446655440000")
    public void testGetCurrentUser() {
        // Given
        User expectedUser = new User();
        expectedUser.setId(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"));
        expectedUser.setPhoneNumber("1234567892");
        userRepository.save(expectedUser); // Assuming UserRepository has a save method

        // When
        var result = userService.getCurrentUser();

        // Then
        assertThat(result).isPresent().get().isEqualTo(expectedUser);
    }

    @Test
    public void testGetCurrentUserNotFound() {
        // Given
        User expectedUser = new User();
        expectedUser.setId(UUID.randomUUID());
        expectedUser.setPhoneNumber("1234567893");
        userRepository.save(expectedUser); // Assuming UserRepository has a save method

        // When
        var result = userService.getCurrentUser();

        // Then
        assertThat(result).isNotPresent();
    }

    @Test
    public void testFindById() {
        // Given
        String phoneNumber = "1234567894";
        User expectedUser = new User();
        expectedUser.setId(UUID.randomUUID());
        expectedUser.setPhoneNumber(phoneNumber);
        userRepository.save(expectedUser); // Assuming UserRepository has a save method

        // When
        var result = userService.findById(expectedUser.getId());

        // Then
        assertThat(result).isPresent().get().isEqualTo(expectedUser);
    }

    @Test
    public void testFindByIdNotFound() {
        // Given
        String phoneNumber = "1234567895";
        User expectedUser = new User();
        expectedUser.setId(UUID.randomUUID());
        expectedUser.setPhoneNumber(phoneNumber);
        userRepository.save(expectedUser); // Assuming UserRepository has a save method

        // When
        var result = userService.findById(UUID.randomUUID());

        // Then
        assertThat(result).isNotPresent();
    }


}

