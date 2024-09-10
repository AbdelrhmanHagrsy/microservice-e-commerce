package com.abdelrahman.customerservice.dto;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CustomerPaymentDto {
    private Long id;
    private PaymentType paymentType;
    private String provider;
    private String accountNo;
    private String expiry;
}
