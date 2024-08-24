package com.abdelrahaman.authenticationservice.dto;

import lombok.Builder;

@Builder
public record RegistirationEmailRequest(
        String to,
        String subject,
        String body
) {
}
