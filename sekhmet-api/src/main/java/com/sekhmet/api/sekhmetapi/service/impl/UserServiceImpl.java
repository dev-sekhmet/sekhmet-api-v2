package com.sekhmet.api.sekhmetapi.service.impl;

import com.sekhmet.api.sekhmetapi.domain.User;
import com.sekhmet.api.sekhmetapi.service.UserService;
import com.sekhmet.api.sekhmetapi.service.dto.sms.CheckPhoneVerificationRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public Optional<User> getUserByPhoneNumber(String phoneNumber) {
        return Optional.empty();
    }

    @Override
    public User registerUserByPhoneNumber(CheckPhoneVerificationRequest request) {
        return new User();
    }
}
