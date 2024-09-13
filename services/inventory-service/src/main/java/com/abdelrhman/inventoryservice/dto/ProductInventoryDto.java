package com.abdelrhman.inventoryservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductInventoryDto {
    private String id;
    @NotBlank
    private String productId;
    @NotNull
    @PositiveOrZero
    private Integer totalQuantity;
    @NotNull
    @PositiveOrZero
    private Integer availableQuantity;
    @NotNull
    @PositiveOrZero
    private Integer damagedQuantity;
    private String location;
    private InventoryStatus inventoryStatus;
}
