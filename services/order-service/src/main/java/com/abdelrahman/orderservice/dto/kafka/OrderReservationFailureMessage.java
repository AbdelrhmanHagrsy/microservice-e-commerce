package com.abdelrahman.orderservice.dto.kafka;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderReservationFailureMessage {
    private OrderCreatedMessage orderCreatedMessage;
    private Boolean isDeducted;
    private String errorMessage;
}
