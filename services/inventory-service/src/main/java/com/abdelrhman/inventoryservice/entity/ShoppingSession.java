package com.abdelrhman.inventoryservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@Document("shopping_session")
public class ShoppingSession {

    @Id
    private String id;
    private Long customerId;
    private String customerUserName;
    private BigDecimal total;
    private LocalDate createdAt;
    private LocalDate modifiedAt;

}
