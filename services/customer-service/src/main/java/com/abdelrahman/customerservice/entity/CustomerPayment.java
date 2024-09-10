package com.abdelrahman.customerservice.entity;

import com.abdelrahman.customerservice.dto.PaymentType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@Entity
@Table(name = "customer_payment")
public class CustomerPayment extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;
    @Column(name = "provider")
    private String provider;
    @Column(name = "account_no")
    private String accountNo;
    @Column(name = "expiry")
    private Date expiry;
    @ManyToOne(targetEntity = Customer.class)
    @JoinColumn(name="user_id",referencedColumnName = "id")
    @JsonIgnore
    private Customer customer;
}
