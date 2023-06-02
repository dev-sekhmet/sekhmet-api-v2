package com.sekhmet.api.sekhmetapi.service.dto.sms;

import com.twilio.rest.verify.v2.service.Verification;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class StartPhoneVerificationRequest {

    @NotNull
    private String phoneNumber;

    @NotNull
    private Verification.Channel channel;

    private String locale;

    public StartPhoneVerificationRequest() {}
}
