package com.abdelrhman.inventoryservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@Document("order_transaction")
public class OrderTransaction {
    @Id
    private UUID id;  // order transactionId
    private Boolean isDeducted;
    private Boolean sendToPayment;
}
