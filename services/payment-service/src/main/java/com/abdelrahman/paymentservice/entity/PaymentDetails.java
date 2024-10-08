package com.abdelrahman.paymentservice.entity;

import com.abdelrahman.paymentservice.dto.kafka.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@Document("payment_details")
public class PaymentDetails {
    @Id
    private String id;
    private String orderId;
    private BigDecimal amount;
    private PaymentStatus paymentStatus;
    private LocalDate createdAt;
    private LocalDate modifiedAt;
}
