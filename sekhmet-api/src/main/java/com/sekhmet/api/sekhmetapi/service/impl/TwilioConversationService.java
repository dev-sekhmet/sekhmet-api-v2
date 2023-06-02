package com.sekhmet.api.sekhmetapi.service.impl;

import com.sekhmet.api.sekhmetapi.service.ConversationService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TwilioConversationService implements ConversationService {
    @Override
    public String generateAccessToken(UUID id) {
        return "null";
    }
}
