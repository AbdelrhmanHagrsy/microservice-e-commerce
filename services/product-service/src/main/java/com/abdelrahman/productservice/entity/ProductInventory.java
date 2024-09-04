package com.abdelrahman.productservice.entity;

import com.abdelrahman.productservice.dto.InventoryStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.Comparator;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@Entity
@Table(name = "product_inventory")
public class ProductInventory extends BaseEntity<Long> {

    @Column(name="price")
    private Integer quantity;

    @Column(name="location")
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(name ="status")
    private InventoryStatus inventoryStatus;
}
