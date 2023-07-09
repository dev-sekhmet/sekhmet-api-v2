package com.sekhmet.api.sekhmetapi.web.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sekhmet.api.sekhmetapi.service.ConversationService;
import com.sekhmet.api.sekhmetapi.service.UserService;
import com.sekhmet.api.sekhmetapi.service.VerificationService;
import com.sekhmet.api.sekhmetapi.service.dto.sms.CheckPhoneVerificationRequest;
import com.sekhmet.api.sekhmetapi.service.dto.sms.StartPhoneVerificationRequest;
import com.sekhmet.api.sekhmetapi.service.dto.sms.VerificationStatus;
import com.sekhmet.api.sekhmetapi.web.rest.error.BadRequestAlertException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Slf4j
public class UserAuthResource {

    private static final String ENTITY_NAME = "user";
    public static final String AUTHORIZATION_HEADER = "Authorization";

    private final VerificationService verificationService;
    private final ConversationService conversationService;
    private final UserService userService;

    /**
     *
     * @param request the phone verification request
     * @return the status
     * @throws BadRequestAlertException errors
     */
    @GetMapping("/authenticate")
    public ResponseEntity<VerificationStatus> authenticate(@Valid StartPhoneVerificationRequest request) throws BadRequestAlertException {
        // for GOOGLE and APPLE verification
        if (request.getPhoneNumber().startsWith("+23799999")) {
            return ResponseEntity.ok(VerificationStatus.PENDING);
        }

        var status = verificationService.sendVerificationCode(request);
        if (status.isEmpty()) {
            throw new BadRequestAlertException("An error occur during Verification Code Send", ENTITY_NAME, "errorSendVerificationCode");
        }
        if (status.get() == VerificationStatus.CANCELED) {
            throw new BadRequestAlertException("An error occurred request canceled", ENTITY_NAME, "errorSendVerificationCodeCanceled");
        }
        return ResponseEntity.ok(status.get());
    }


    /**
     * Login or signup via phone number
     *
     * @param request : verification token, phone number, langKey and token request
     * @return JWTToken the token created
     */
    @GetMapping("/verify")
    public ResponseEntity<JWTToken> verify(CheckPhoneVerificationRequest request) throws BadRequestAlertException {
        // for GOOGLE and APPLE verification
        if (request.getPhoneNumber().startsWith("+23799999") && request.getToken().startsWith("9999")) {
            return generateJwtTokenAndOrCreateUser(request);
        }

        var status = verificationService.checkVerificationCode(request);
        if (status.isEmpty()) {
            throw new BadRequestAlertException("An error occur during Verification Code Send", ENTITY_NAME, "errorCheckVerificationCode");
        }
        if (status.get() == VerificationStatus.CANCELED) {
            throw new BadRequestAlertException("An error occurred request canceled", ENTITY_NAME, "errorCheckVerificationCodeCanceled");
        }
        if (status.get() == VerificationStatus.PENDING) {
            throw new BadRequestAlertException(
                    "An error occurred request pending (wrong code)",
                    ENTITY_NAME,
                    "errorCheckVerificationIncorrectCode"
            );
        }

        return generateJwtTokenAndOrCreateUser(request);
    }

    /**
     * Login or signup via phone number
     *
     * @return
     */
    @GetMapping("/refresh-twilio-token")
    public ResponseEntity<JWTToken> refreshTwilioToken(CheckPhoneVerificationRequest request) {
        log.info("Refresh twilio token request: {}", request);
        return generateJwtTokenAndOrCreateUser(request);
    }

    private ResponseEntity<JWTToken> generateJwtTokenAndOrCreateUser(CheckPhoneVerificationRequest request) {
        log.debug("Find or create user by phone number: {}", request.getPhoneNumber());
        var userOptional = userService.getUserByPhoneNumber(request.getPhoneNumber());
        var user = userOptional.orElseGet(() -> userService.registerUserByPhoneNumber(request));

        HttpHeaders httpHeaders = new HttpHeaders();
        log.info("Generate JWT token request: {}", request);
        String twilioToken = conversationService.generateAccessToken(user.getId());
        httpHeaders.add(AUTHORIZATION_HEADER, "Bearer " + twilioToken);
        return new ResponseEntity<>(new JWTToken(twilioToken), httpHeaders, HttpStatus.OK);
    }

    /**
     * Object to return as body in JWT Authentication.
     */
    static class JWTToken {

        private String idToken;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }


        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }
}
