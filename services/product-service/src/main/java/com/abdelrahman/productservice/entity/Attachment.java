package com.abdelrahman.productservice.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Table(name = "attachment")
public class Attachment extends BaseEntity<Long> {

    private String originalFileName;

    @Column(unique = true)
    private String uniqueAwsFileName;

    @ManyToOne
    @JoinColumn(name = "product_id",referencedColumnName = "id")
    private Product product;
}
