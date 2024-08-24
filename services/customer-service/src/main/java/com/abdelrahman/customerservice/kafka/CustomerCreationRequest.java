package com.abdelrahman.customerservice.kafka;

import lombok.*;



@Builder
public record CustomerCreationRequest(
        String userName,
        String password,
        String firstName,
        String lastName,
        String telephone
) {
}
