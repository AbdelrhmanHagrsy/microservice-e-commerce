package com.abdelrahman.productservice.dto;

import jakarta.persistence.Lob;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OfferDto {
    private String message;
    private String imageName;
    private String imageContent;
    private Boolean isActive;
    private ProductCategoryDto productCategoryDto;
}
