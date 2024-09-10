package com.abdelrahman.productservice.dto.request;

import com.abdelrahman.productservice.dto.DiscountDto;
import com.abdelrahman.productservice.dto.ProductCategoryDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductRequest {

    private Long id;

    @NotNull(message = "Product name cannot be null.")
    @NotEmpty(message = "Product name cannot be empty.")
    private String productName;

    @Size(max = 500, message = "Description cannot exceed 500 characters.")
    private String description;

    @NotNull(message = "SKU cannot be null.")
    @NotEmpty(message = "SKU cannot be empty.")
    private String sku;

    @NotNull(message = "Price cannot be null.")
    @Positive(message = "Price must be a positive value.")
    private BigDecimal price;

    private ProductCategoryDto productCategoryDto;

    private DiscountDto discountDto;
}
