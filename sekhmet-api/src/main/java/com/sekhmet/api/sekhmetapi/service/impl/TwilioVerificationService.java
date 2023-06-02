package com.sekhmet.api.sekhmetapi.service.impl;

import com.sekhmet.api.sekhmetapi.service.VerificationService;
import com.sekhmet.api.sekhmetapi.service.dto.sms.CheckPhoneVerificationRequest;
import com.sekhmet.api.sekhmetapi.service.dto.sms.StartPhoneVerificationRequest;
import com.sekhmet.api.sekhmetapi.service.dto.sms.VerificationStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TwilioVerificationService implements VerificationService {
    @Override
    public Optional<VerificationStatus> sendVerificationCode(StartPhoneVerificationRequest request) {
        return Optional.empty();
    }

    @Override
    public Optional<VerificationStatus> checkVerificationCode(CheckPhoneVerificationRequest request) {
        return Optional.empty();
    }
}
