package com.sekhmet.api.sekhmetapi.converters;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.sekhmet.api.sekhmetapi.domain.Authority;
import io.jsonwebtoken.lang.Collections;

import java.util.Set;
import java.util.stream.Collectors;

public class AuthoritySetConverter implements DynamoDBTypeConverter<Set<String>, Set<Authority>> {

    @Override
    public Set<String> convert(Set<Authority> authorities) {
        if (Collections.isEmpty(authorities)){
            return null;
        }
        return authorities.stream().map(Authority::getName).collect(Collectors.toSet());
    }

    @Override
    public Set<Authority> unconvert(Set<String> authorities) {
        if (Collections.isEmpty(authorities)){
            return null;
        }
        return authorities.stream().map(name -> {
            Authority authority = new Authority();
            authority.setName(name);
            return authority;
        }).collect(Collectors.toSet());
    }
}
