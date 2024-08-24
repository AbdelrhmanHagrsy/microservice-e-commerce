package com.example.emailnotifservice.dto;

public record RegistirationEmailRequest(
        String to,
        String subject,
        String body
) {
}
