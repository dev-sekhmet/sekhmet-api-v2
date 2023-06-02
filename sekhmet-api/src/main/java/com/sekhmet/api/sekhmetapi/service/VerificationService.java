package com.sekhmet.api.sekhmetapi.service;

import com.sekhmet.api.sekhmetapi.service.dto.sms.CheckPhoneVerificationRequest;
import com.sekhmet.api.sekhmetapi.service.dto.sms.StartPhoneVerificationRequest;
import com.sekhmet.api.sekhmetapi.service.dto.sms.VerificationStatus;

import java.util.Optional;

public interface VerificationService {
    Optional<VerificationStatus> sendVerificationCode(StartPhoneVerificationRequest request);

    Optional<VerificationStatus> checkVerificationCode(CheckPhoneVerificationRequest request);
}
