package com.abdelrahman.productservice.dto;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductInventoryDto {
    private Long id;
    private Integer quintity;
    private String location;
    private InventoryStatus inventoryStatus;
}
