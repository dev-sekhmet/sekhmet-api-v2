package com.sekhmet.api.sekhmetapi.service.impl;

import com.sekhmet.api.sekhmetapi.domain.User;
import com.sekhmet.api.sekhmetapi.repository.UserRepository;
import com.sekhmet.api.sekhmetapi.security.SecurityUtils;
import com.sekhmet.api.sekhmetapi.service.UserService;
import com.sekhmet.api.sekhmetapi.service.dto.sms.CheckPhoneVerificationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    public static final String ACCOUNT_USER_PROFIL_PICTURE = "account/user-profil-picture";
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

    @Override
    public Optional<User> getCurrentUser() {
        return SecurityUtils.getCurrentUserLogin().flatMap(id -> userRepository.findById(UUID.fromString(id)));
    }

    @Override
    public Optional<User> updateUser(String firstName, String lastName, String email, String langKey, String imageUrl) {
        return SecurityUtils
                .getCurrentUserLogin()
                .flatMap(id -> userRepository.findById(UUID.fromString(id)))
                .map(user -> {
                            user.setFirstName(firstName);
                            user.setLastName(lastName);
                            if (email != null) {
                                user.setEmail(email.toLowerCase());
                            }
                            user.setLangKey(langKey);
                            user.setImageUrl(imageUrl);
                            log.debug("Changed Information for User: {}", user);
                            return userRepository.save(user);
                        }
                );
    }

    @Override
    public Optional<User> findById(UUID id) {
        return userRepository.findById(id);
    }

}
