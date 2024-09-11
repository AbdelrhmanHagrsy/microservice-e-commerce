package com.abdelrahman.orderservice.entity;

import com.abdelrahman.orderservice.dto.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@Document("order_collection")
public class Order {
    @Id
    private String id;
    private String customerId;
    private String customerUserName;
    private UUID transactionId;
    private BigDecimal total;
    private String paymentId;
    private OrderStatus orderStatus;
    private LocalDate createdAt;
    private LocalDate modifiedAt;
}
