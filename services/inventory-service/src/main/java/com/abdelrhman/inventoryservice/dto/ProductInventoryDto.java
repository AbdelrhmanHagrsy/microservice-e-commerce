package com.abdelrhman.inventoryservice.dto;

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
    private String productId;
    private Integer totalQuantity;
    private String location;
    private InventoryStatus inventoryStatus;
}
