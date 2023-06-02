package com.sekhmet.api.sekhmetapi.converters;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

import java.time.Instant;

public class InstantConverter implements DynamoDBTypeConverter<String, Instant> {
    @Override
    public String convert(Instant instant) {
        if (instant == null) {
            return null;
        }
        return instant.toString();
    }

    @Override
    public Instant unconvert(String instantString) {
        if (instantString == null || instantString.isEmpty()) {
            return null;
        }
        return Instant.parse(instantString);
    }
}
