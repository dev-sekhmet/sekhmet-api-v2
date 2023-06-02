package com.sekhmet.api.sekhmetapi.converters;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

import java.util.UUID;
public class UuidConverter implements DynamoDBTypeConverter<String, UUID> {
    @Override
    public String convert(UUID uuid) {
        if (uuid == null) {
            return null;
        }
        return uuid.toString();
    }

    @Override
    public UUID unconvert(String uuidString) {
        if (uuidString == null || uuidString.isEmpty()) {
            return null;
        }
        return UUID.fromString(uuidString);
    }
}
