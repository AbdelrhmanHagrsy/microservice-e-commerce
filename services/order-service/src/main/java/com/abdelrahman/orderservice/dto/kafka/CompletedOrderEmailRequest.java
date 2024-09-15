package com.abdelrahman.orderservice.dto.kafka;

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
public class CompletedOrderEmailRequest {

    private String customerId;
    private String customerUserName;
    private BigDecimal total;
    private List<OrderItemDto> orderItemDtoList;
}
