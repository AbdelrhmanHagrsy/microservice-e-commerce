package com.abdelrahman.productservice.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductCategoryDto {

    @Positive(message = "Product category ID must be a positive value.")
    private Long id;
    @NotNull(message = "Category name cannot be null.")
    @NotEmpty(message = "Category name cannot be empty.")
    private String categoryName;
    private String description;
}
