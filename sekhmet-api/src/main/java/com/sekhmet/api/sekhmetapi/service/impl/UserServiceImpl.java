package com.sekhmet.api.sekhmetapi.service.impl;

import com.sekhmet.api.sekhmetapi.domain.User;
import com.sekhmet.api.sekhmetapi.repository.UserRepository;
import com.sekhmet.api.sekhmetapi.service.UserService;
import com.sekhmet.api.sekhmetapi.service.dto.sms.CheckPhoneVerificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    @Override
    public Optional<User> getUserByPhoneNumber(String phoneNumber) {
        return userRepository.findUserByPhoneNumber(phoneNumber);
    }

    @Override
    public User registerUserByPhoneNumber(CheckPhoneVerificationRequest request) {
        var user = new User();
        user.setId(UUID.randomUUID());
        user.setPhoneNumber(request.getPhoneNumber());
        return userRepository.save(user);
    }
}
