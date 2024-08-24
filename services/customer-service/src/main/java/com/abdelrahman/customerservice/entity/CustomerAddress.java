package com.abdelrahman.customerservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@Entity
@Table(name = "customer_address")
public class CustomerAddress extends BaseEntity{

    @Column(name = "address_line1")
    private String addressLineOne;
    @Column(name = "address_line2")
    private String addressLineTwo;
    @Column(name = "city")
    private String city;
    @Column(name = "postal_code")
    private String postalCode;
    @Column(name = "country")
    private String country;
    @Column(name = "telephone")
    private String telephone;
    @Column(name = "mobile")
    private String mobile;
    @OneToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private Customer customer;
}
