package com.abdelrahman.orderservice.mapper;

import com.abdelrahman.orderservice.dto.kafka.OrderRequest;
import com.abdelrahman.orderservice.dto.kafka.OrderCreatedMessage;
import com.abdelrahman.orderservice.entity.Order;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OrderMapper {

    public OrderCreatedMessage prepareOrderMessage(Order order, OrderRequest orderRequest){
        return OrderCreatedMessage.builder()
                .orderId(order.getId())
                .idempotentKey(UUID.randomUUID().toString()) // CREATE RANDOM UNIQUE KEY FOR EACH ORDER TO PREVENT DUPLICATE DEDUCT IN INVENTORY SERVICE
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
