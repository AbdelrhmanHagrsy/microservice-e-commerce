package com.abdelrahman.paymentservice.dto.kafka;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderCreatedMessage {
    private String orderId;
    private String customerId;
    private String customerUserName;
    private UUID transactionId;
    private BigDecimal total;
    private String paymentId;
    private OrderStatus orderStatus;
    private List<OrderItemDto> orderItemDtoList;
}