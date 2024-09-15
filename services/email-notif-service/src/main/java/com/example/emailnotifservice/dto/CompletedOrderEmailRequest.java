package com.example.emailnotifservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

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
