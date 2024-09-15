package com.abdelrahman.orderservice.dto.kafka;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CancelOrderInventoryDeductionRequest {
    private List<OrderItemDto> orderItemDtoList;
}
