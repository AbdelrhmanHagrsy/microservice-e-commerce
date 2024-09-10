package com.abdelrhman.inventoryservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@Document("cart_item")
public class CartItem {
    @Id
    private String id;
    private String sessionId;
    private String productId;
    private Integer quantity;
    private LocalDate createdAt;
    private LocalDate modifiedAt;
}
