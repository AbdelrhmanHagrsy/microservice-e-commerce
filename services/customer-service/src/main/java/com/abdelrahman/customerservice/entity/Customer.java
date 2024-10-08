package com.abdelrahman.customerservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;

import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@Entity
@Table(name = "customer")
public class Customer extends BaseEntity {

    @Column(name = "user_name",unique = true)
    private String userName;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "telephone")
    private String telephone;
    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "address_id",referencedColumnName = "id")
    private CustomerAddress customerAddress;
}
