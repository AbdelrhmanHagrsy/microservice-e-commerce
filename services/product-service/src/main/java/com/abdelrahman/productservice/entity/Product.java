package com.abdelrahman.productservice.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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

    @ManyToOne
    @JoinColumn(name = "category_id",referencedColumnName = "id")
    private ProductCategory productCategory;
    @ManyToOne
    @JoinColumn(name = "discount_id",referencedColumnName = "id")
    private Discount discount;
    @OneToMany(mappedBy = "product")
    private Set<Attachment> attachments;
}
