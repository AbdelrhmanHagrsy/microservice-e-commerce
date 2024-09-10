package com.abdelrhman.inventoryservice.entity;

import com.abdelrhman.inventoryservice.dto.InventoryStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@Document("inventory_collection")
public class ProductInventory  {

    @Id
    private String id;

    @Column(unique = true)
    private String productId;

    private Integer totalQuantity;

    private Integer availableQuantity;

    private Integer damagedQuantity;

    private String location;

    @Enumerated(EnumType.STRING)
    private InventoryStatus inventoryStatus;
}