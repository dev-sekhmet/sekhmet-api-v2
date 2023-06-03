package com.sekhmet.api.sekhmetapi.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sekhmet.api.sekhmetapi.config.ApplicationProperties;
import com.sekhmet.api.sekhmetapi.service.ConversationService;
import com.sekhmet.api.sekhmetapi.service.UserService;
import com.twilio.jwt.accesstoken.AccessToken;
import com.twilio.jwt.accesstoken.ChatGrant;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TwilioConversationService implements ConversationService {

    public static final String DUAL_CONVERSATION_FORMAT_ID = "DUAL_%s_%s";
    public static final String GROUP_CONVERSATION_FORMAT_ID = "GROUP_%s";
    public static final int PAGE_SIZE = 10000;
    private final UserService userService;
    private final ObjectMapper objectMapper;
    private final ApplicationProperties.TwilioProperties twilioProperties;

    public TwilioConversationService(UserService userService, ApplicationProperties applicationProperties) {
        this.twilioProperties = applicationProperties.getTwilio();
        this.userService = userService;
        objectMapper = new ObjectMapper();
    }

    @Override
    public String generateAccessToken(UUID id) {
        ChatGrant grant = new ChatGrant();
        grant.setServiceSid(twilioProperties.getConversationSid());

        AccessToken token = new AccessToken
                .Builder(twilioProperties.getAccountSid(), twilioProperties.getApiSid(), twilioProperties.getApiSecret())
                .identity(id.toString())
                .grant(grant)
                .ttl(86400) // 24 hours
                .build();
        return token.toJwt();
    }
}
