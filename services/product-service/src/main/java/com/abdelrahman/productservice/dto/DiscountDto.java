package com.abdelrahman.productservice.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DiscountDto {
    @Positive(message = "Discount ID must be a positive value.")
    private Long id;
    private String discountName;
    private String description;
    private Boolean active;
    private BigDecimal discountPercent;
}
