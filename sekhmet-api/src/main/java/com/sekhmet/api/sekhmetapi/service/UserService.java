package com.sekhmet.api.sekhmetapi.service;

import com.amazonaws.services.s3.model.S3Object;
import com.sekhmet.api.sekhmetapi.domain.User;
import com.sekhmet.api.sekhmetapi.service.dto.sms.CheckPhoneVerificationRequest;
import com.sekhmet.api.sekhmetapi.service.dto.sms.VerificationStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

public interface UserService {

    Optional<User> getUserByPhoneNumber(String phoneNumber);

    User registerUserByPhoneNumber(CheckPhoneVerificationRequest request);

    Optional<User> getUserWithAuthorities();
    /**
     * Update basic information (first name, last name, email, language) for the current user.
     *
     * @param firstName first name of user.
     * @param lastName  last name of user.
     * @param email     email id of user.
     * @param langKey   language key.
     * @param imageUrl  image URL of user.
     */
    void updateUser(String firstName, String lastName, String email, String langKey, String imageUrl);

    Optional<User> findById(UUID uuid);

    Optional<User> addProfilePicture(String userLogin, MultipartFile file);

    S3Object getProfiPic(String userId, String fileId);
}
