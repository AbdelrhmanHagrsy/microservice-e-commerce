package com.abdelrahman.orderservice.mapper;

import com.abdelrahman.orderservice.dto.kafka.OrderRequest;
import com.abdelrahman.orderservice.dto.kafka.OrderCreatedMessage;
import com.abdelrahman.orderservice.entity.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public OrderCreatedMessage prepareOrderMessage(Order order, OrderRequest orderRequest){
        return OrderCreatedMessage.builder()
                .orderId(order.getId())
                .idempotentKey(orderRequest.getIdempotentKey())
                .customerId(order.getCustomerId())
                .customerUserName(order.getCustomerUserName())
                .paymentId(order.getPaymentId())
                .orderStatus(order.getOrderStatus())
                .orderItemDtoList(orderRequest.getOrderItemDtoList())
                .total(order.getTotal())
                .paymentToken(orderRequest.getPaymentToken())
                .build();
    }
}
