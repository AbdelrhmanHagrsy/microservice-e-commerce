package com.abdelrahman.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderRequest {
    private String customerID;
    private String customerUserName;
    private BigDecimal totalPrice;
    private List<OrderItemDto> orderItemDtoList;
}
