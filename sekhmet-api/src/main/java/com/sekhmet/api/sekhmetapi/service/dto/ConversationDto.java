package com.sekhmet.api.sekhmetapi.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class ConversationDto {

    private List<UUID> ids;
    private String friendlyName;
    private String description;
}
