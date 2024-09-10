package com.abdelrhman.inventoryservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CartItemDto {
    private String id;
    @NotBlank
    private String sessionId;
    @NotBlank
    private String productId;
    @Positive(message = "Quantity must be a positive value.")
    private Integer quantity;
    private LocalDate createdAt;
    private LocalDate modifiedAt;
}
