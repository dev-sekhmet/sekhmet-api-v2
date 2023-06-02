package com.sekhmet.api.sekhmetapi.domain;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sekhmet.api.sekhmetapi.converters.AuthoritySetConverter;
import com.sekhmet.api.sekhmetapi.converters.InstantConverter;
import com.sekhmet.api.sekhmetapi.converters.UuidConverter;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * A user.
 */
@DynamoDBTable(tableName = "User")
@EqualsAndHashCode
public class User  implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private UUID id;

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
    @DynamoDBHashKey
    @DynamoDBTypeConverted(converter = UuidConverter.class)
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @DynamoDBAttribute
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    @DynamoDBAttribute
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    @DynamoDBAttribute
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @DynamoDBAttribute
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    @DynamoDBAttribute
    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }
    @DynamoDBAttribute
    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }
    @DynamoDBAttribute
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    @DynamoDBAttribute
    public String getActivationKey() {
        return activationKey;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }
    @DynamoDBAttribute
    public String getResetKey() {
        return resetKey;
    }

    public void setResetKey(String resetKey) {
        this.resetKey = resetKey;
    }
    @DynamoDBAttribute
    @DynamoDBTypeConverted(converter = InstantConverter.class)
    public Instant getResetDate() {
        return resetDate;
    }

    public void setResetDate(Instant resetDate) {
        this.resetDate = resetDate;
    }
    @DynamoDBAttribute
    @DynamoDBTypeConverted(converter = AuthoritySetConverter.class)
    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }
}
