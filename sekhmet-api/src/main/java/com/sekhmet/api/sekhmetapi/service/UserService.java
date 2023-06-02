package com.sekhmet.api.sekhmetapi.service;

import com.sekhmet.api.sekhmetapi.domain.User;
import com.sekhmet.api.sekhmetapi.service.dto.sms.CheckPhoneVerificationRequest;
import com.sekhmet.api.sekhmetapi.service.dto.sms.VerificationStatus;

import java.util.Optional;

public interface UserService {
    Optional<User> getUserByPhoneNumber(String phoneNumber);

    User registerUserByPhoneNumber(CheckPhoneVerificationRequest request);
}
