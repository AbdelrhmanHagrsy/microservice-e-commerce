package com.abdelrahman.paymentservice.entity;

import com.abdelrahman.paymentservice.dto.kafka.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@Document("customer_payment")
public class CustomerPayment  {

    @Id
    private String id;
    private String customerId;
    private PaymentType paymentType;
    private String provider;
    private String accountNo;
    private Date expiry;
}
