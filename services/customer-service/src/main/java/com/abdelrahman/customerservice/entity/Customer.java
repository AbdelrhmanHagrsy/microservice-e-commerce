package com.abdelrahman.customerservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@Entity
@Table(name = "customer")
public class Customer extends BaseEntity {

    @Column(name = "user_name")
    private String userName;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "telephone")
    private String telephone;
    @OneToMany(mappedBy = "customer")
    private Set<CustomerPayment> customerPaymentSet;
    @Column(name = "is_admin")
    private Boolean isAdmin;
    @ManyToOne
    @JoinColumn(name = "admin_type_id",referencedColumnName = "id")
    private AdminType adminType;
}
