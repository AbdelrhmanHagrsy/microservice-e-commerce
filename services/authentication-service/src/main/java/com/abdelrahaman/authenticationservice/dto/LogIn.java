package com.abdelrahaman.authenticationservice.dto;

import jakarta.validation.constraints.Email;
import org.jetbrains.annotations.NotNull;

public record LogIn(
        @NotNull(value = "Username is required")
        @Email
        String userName,
        @NotNull(value = "Password is required")
        String password
) {
}
