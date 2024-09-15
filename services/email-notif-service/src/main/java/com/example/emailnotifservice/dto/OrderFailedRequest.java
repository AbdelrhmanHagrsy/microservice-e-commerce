package com.example.emailnotifservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderFailedRequest {
    private String customerUserName;
    private BigDecimal total;
    private String errorMessage;
}
