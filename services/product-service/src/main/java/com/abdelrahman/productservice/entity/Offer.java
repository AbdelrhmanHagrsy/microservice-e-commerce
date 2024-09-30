package com.abdelrahman.productservice.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Table(name = "offer")
public class Offer extends BaseEntity<Long> {

    private String message;
    private String imageName;
    @Lob // To store large binary data
    private String imageContent;

    private Boolean isActive;
    @OneToOne
    @JoinColumn(name = "product_category_id" , referencedColumnName = "id")
    private ProductCategory productCategory;
}
