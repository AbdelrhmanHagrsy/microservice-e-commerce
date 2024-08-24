package com.abdelrahman.customerservice.entity;

import com.abdelrahman.customerservice.dto.PaymentType;
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
    private Integer accountNo;
    @Column(name = "expiry")
    private Date expiry;
    @ManyToOne
    @JoinColumn(name="user_id",referencedColumnName = "id")
    private Customer customer;
}
