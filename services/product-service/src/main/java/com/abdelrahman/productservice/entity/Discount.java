package com.abdelrahman.productservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@Entity
@Table(name = "discount")
public class Discount extends BaseEntity<Long> {
    @Column(name="name")
    private String name;
    @Column(name="description")
    private String desc;
    @Column(name="active")
    private Boolean active;
    @Column(name="discount_percentage")
    private BigDecimal discountPercent;
    @OneToMany(mappedBy = "discount")
    private Set<Product> productSet;
}
