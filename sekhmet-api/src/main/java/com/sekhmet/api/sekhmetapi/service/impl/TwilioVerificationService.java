package com.sekhmet.api.sekhmetapi.service.impl;

import com.sekhmet.api.sekhmetapi.config.ApplicationProperties;
import com.sekhmet.api.sekhmetapi.service.VerificationService;
import com.sekhmet.api.sekhmetapi.service.dto.sms.CheckPhoneVerificationRequest;
import com.sekhmet.api.sekhmetapi.service.dto.sms.StartPhoneVerificationRequest;
import com.sekhmet.api.sekhmetapi.service.dto.sms.VerificationStatus;
import com.twilio.Twilio;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class TwilioVerificationService implements VerificationService {
    private final ApplicationProperties.TwilioProperties twilioProperties;

    public TwilioVerificationService(ApplicationProperties applicationProperties) {
        this.twilioProperties = applicationProperties.getTwilio();
        Twilio.init(twilioProperties.getAccountSid(), twilioProperties.getAuthToken());
    }

    @Override
    public Optional<VerificationStatus> sendVerificationCode(StartPhoneVerificationRequest request) {
        Verification verification = Verification
                .creator(
                        twilioProperties.getVerifySid(),
                        request.getPhoneNumber(), // concatenated with country code +33 or +237
                        request.getChannel().toString()
                )
                .create();
        var status = verification.getStatus();
        log.info("Verification code send, params: {} - status: {}", request, status);
        return Optional.ofNullable(VerificationStatus.forValue(status));
    }

    @Override
    public Optional<VerificationStatus> checkVerificationCode(CheckPhoneVerificationRequest request) {
        VerificationCheck verificationCheck = VerificationCheck
                .creator(twilioProperties.getVerifySid(), request.getToken())
                .setTo(request.getPhoneNumber())
                .create();

        var status = verificationCheck.getStatus();
        log.info("Verification code send, params: {} - status: {}", request, status);
        return Optional.ofNullable(VerificationStatus.forValue(status));
    }

}
