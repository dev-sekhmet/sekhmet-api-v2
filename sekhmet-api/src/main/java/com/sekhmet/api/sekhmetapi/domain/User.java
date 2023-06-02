package com.sekhmet.api.sekhmetapi.domain;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * A user.
 */
@DynamoDBTable(tableName = "Dev_User")
@Data
public class User  implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @DynamoDBHashKey
    private UUID id;

    private String login;

    @JsonIgnore
    private String password;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private boolean activated = false;

    private String langKey;

    private String imageUrl;

    @JsonIgnore
    private String activationKey;

    @JsonIgnore
    private String resetKey;

    private Instant resetDate = null;

    @JsonIgnore
    private Set<Authority> authorities = new HashSet<>();

}
