package com.abdelrahman.productservice.entity;

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
@Table(name = "product_category")
public class ProductCategory extends BaseEntity<Long>{
    @Column(name="name")
    private String name;
    @Column(name="description")
    private String desc;
    @OneToMany(mappedBy = "productCategory")
    private Set<Product> productSet;
}
