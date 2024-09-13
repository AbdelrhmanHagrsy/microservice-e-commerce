package com.abdelrahman.paymentservice.dto.kafka;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CustomerPaymentDto {
    private String id;
    private String customerId;
    private PaymentType paymentType;
    private String provider;
    private String accountNo;
    private String expiry;
}
