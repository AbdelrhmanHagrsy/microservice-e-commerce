package com.abdelrhman.productnosqlservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@Document("product_collection")
public class Product {
    @Id
    private String id;
    private String productName;
    private String categoryName;
    private String desc;
    private String sku;
    private BigDecimal price;
    private Integer quantity;
    private String discountName;
    private Boolean discountActive;
    private BigDecimal discountPercent;
    private Long productId;
    private Long productInventoryId;
    private Long productCategoryId;
    private Long discountId;
    private LocalDate lastModifiedDate;

}
