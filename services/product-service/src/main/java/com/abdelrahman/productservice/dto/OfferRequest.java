package com.abdelrahman.productservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class OfferRequest {
        private String message;
        private Integer productCategoryId;
}


