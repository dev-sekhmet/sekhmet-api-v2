package com.sekhmet.api.sekhmetapi.service.impl;

import com.amazonaws.services.s3.model.S3Object;
import com.sekhmet.api.sekhmetapi.domain.User;
import com.sekhmet.api.sekhmetapi.repository.UserRepository;
import com.sekhmet.api.sekhmetapi.security.SecurityUtils;
import com.sekhmet.api.sekhmetapi.service.UserService;
import com.sekhmet.api.sekhmetapi.service.dto.sms.CheckPhoneVerificationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    public Optional<User> getUserWithAuthorities() {
        return SecurityUtils.getCurrentUserLogin().flatMap(id -> userRepository.findById(UUID.fromString(id)));
    }

    @Override
    public void updateUser(String firstName, String lastName, String email, String langKey, String imageUrl) {
        SecurityUtils
                .getCurrentUserLogin()
                .flatMap(id -> userRepository.findById(UUID.fromString(id)))
                .ifPresent(user -> {
                            user.setFirstName(firstName);
                            user.setLastName(lastName);
                            if (email != null) {
                                user.setEmail(email.toLowerCase());
                            }
                            user.setLangKey(langKey);
                            user.setImageUrl(imageUrl);
                            log.debug("Changed Information for User: {}", user);
                        }
                );
    }

    @Override
    public Optional<User> findById(UUID id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> addProfilePicture(String userLogin, MultipartFile file) {
        return Optional.empty();
    }

    @Override
    public S3Object getProfiPic(String userId, String fileId) {
        return null;
    }
}
