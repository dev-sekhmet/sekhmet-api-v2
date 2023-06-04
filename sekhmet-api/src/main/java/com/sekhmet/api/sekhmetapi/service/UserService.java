package com.sekhmet.api.sekhmetapi.service;

import com.sekhmet.api.sekhmetapi.domain.User;
import com.sekhmet.api.sekhmetapi.service.dto.sms.CheckPhoneVerificationRequest;

import java.util.Optional;
import java.util.UUID;

public interface UserService {

    /**
     * Get the user by phone number
     *
     * @param phoneNumber the phone number
     * @return the user or empty
     */
    Optional<User> getUserByPhoneNumber(String phoneNumber);

    /**
     * Register a user by phone number
     *
     * @param request the request
     * @return the user
     */
    User registerUserByPhoneNumber(CheckPhoneVerificationRequest request);

    /**
     * Get the current user
     *
     * @return the current user or empty
     */
    Optional<User> getCurrentUser();

    /**
     * Update basic information (first name, last name, email, language) for the current user.
     *
     * @param firstName first name of user.
     * @param lastName  last name of user.
     * @param email     email id of user.
     * @param langKey   language key.
     * @param imageUrl  image URL of user.
     */
    Optional<User> updateUser(String firstName, String lastName, String email, String langKey, String imageUrl);

    /**
     * get the user bu uuid
     *
     * @param uuid id of the user
     * @return the user or empty
     */
    Optional<User> findById(UUID uuid);

}
