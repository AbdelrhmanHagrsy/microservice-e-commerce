package com.abdelrhman.inventoryservice.dto.kafka;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ReservedInventoryOrderMessage {

    private OrderCreatedMessage orderCreatedMessage;
    private Boolean isDeducted;
}
