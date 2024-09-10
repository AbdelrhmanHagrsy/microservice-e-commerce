package com.abdelrahman.productservice.dto.kafka;

import com.abdelrahman.productservice.dto.ProductMessageStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductMessage {
    private String productName;
    private String categoryName;
    private String desc;
    private String sku;
    private BigDecimal price;
    private String discountName;
    private Boolean discountActive;
    private BigDecimal discountPercent;
    private Long productId;
    private Long productCategoryId;
    private Long discountId;
    private ProductMessageStatus productMessageStatus;
    private LocalDate lastModifiedDate;

}
