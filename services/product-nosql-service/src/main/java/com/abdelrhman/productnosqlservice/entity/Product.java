package com.abdelrhman.productnosqlservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;


@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@Document("product_collection")
public class Product {
    @Id
    private String id;
    private String productName;
    private String desc;
    private String sku;
    private BigDecimal price;
    private Integer productId;
    private Integer productInventoryId;
    private Integer productCategoryId;
    private Integer discountId;
}
