package com.sekhmet.api.sekhmetapi.service.dto.sms;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@AllArgsConstructor
@ToString
public class CheckPhoneVerificationRequest {

    @NotNull
    private String phoneNumber;

    @NotNull
    private String token;

    @Size(min = 2, max = 10)
    private String langKey;

    private String locale;

    public CheckPhoneVerificationRequest() {}
}
