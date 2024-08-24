package com.abdelrahman.customerservice.entity;

import com.abdelrahman.customerservice.dto.AdminTypeEnum;
import com.abdelrahman.customerservice.dto.Permission;
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
@Table(name = "admin_type")
public class AdminType extends BaseEntity{

    @Enumerated(EnumType.STRING)
    private AdminTypeEnum adminType;
    @Enumerated(EnumType.STRING)
    private Permission permission;
    @OneToMany (mappedBy = "adminType")
    private Set<Customer> customers;

}
