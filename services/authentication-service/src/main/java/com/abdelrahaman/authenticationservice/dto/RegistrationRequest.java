package com.abdelrahaman.authenticationservice.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


public record RegistrationRequest(
        @NotNull(message = "User Name is mandatory!")
        @NotEmpty(message = "User Name is mandatory!")
        @Email(message = "Invalid email format!")
        String userName,

        @NotNull(message = "Password is mandatory!")
        @NotEmpty(message = "Password is mandatory!")
        @Size(min = 8, message = "Password must be at least 8 characters long!")
         String password,

        @NotNull(message = "First Name is mandatory!")
        @NotEmpty(message = "First Name is mandatory!")
        String firstName,

        @NotNull(message = "Last Name is mandatory!")
        @NotEmpty(message = "Last Name is mandatory!")
         String lastName,

        @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Invalid telephone number!")
        String telephone
) {
}
