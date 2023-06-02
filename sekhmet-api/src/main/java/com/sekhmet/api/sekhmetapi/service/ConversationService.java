package com.sekhmet.api.sekhmetapi.service;

import java.util.UUID;

public interface ConversationService {
    String generateAccessToken(UUID id);
}
