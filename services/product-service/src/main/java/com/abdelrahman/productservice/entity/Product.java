package com.abdelrahman.productservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@Entity
@Table(name = "product")
public class Product extends BaseEntity<Long> {

    @Column(name="name")
    private String name;
    @Column(name="description")
    private String desc;
    @Column(name="sku",unique = true)
    private String sku;
    @Column(name="price")
    private BigDecimal price;
    @OneToOne
    @JoinColumn(name = "inventory_id",referencedColumnName = "id")
    private ProductInventory productInventory;
    @ManyToOne
    @JoinColumn(name = "category_id",referencedColumnName = "id")
    private ProductCategory productCategory;
    @ManyToOne
    @JoinColumn(name = "discount_id",referencedColumnName = "id")
    private Discount discount;
}
