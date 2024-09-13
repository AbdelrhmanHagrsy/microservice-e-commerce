package com.abdelrahman.orderservice.service;

import com.abdelrahman.orderservice.dto.kafka.OrderItemDto;
import com.abdelrahman.orderservice.dto.kafka.OrderRequest;
import com.abdelrahman.orderservice.dto.kafka.OrderStatus;
import com.abdelrahman.orderservice.dto.kafka.OrderCreatedMessage;
import com.abdelrahman.orderservice.entity.Order;
import com.abdelrahman.orderservice.entity.OrderItem;
import com.abdelrahman.orderservice.exception.InvalidOrderException;
import com.abdelrahman.orderservice.mapper.OrderMapper;
import com.abdelrahman.orderservice.repository.OrderItemRepository;
import com.abdelrahman.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static com.abdelrahman.orderservice.constant.Constant.KafkaConst.KAFKA_ORDER_CREATED_TOPIC_NAME;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderMapper orderMapper;

    private final KafkaTemplate<String, OrderCreatedMessage> kafkaTemplate;


    @Transactional
    public ResponseEntity<?> createOrder(OrderRequest orderRequest) {

        try {
            // validate total price
            BigDecimal itemTotalPrice = orderRequest.getOrderItemDtoList().stream()
                    .map(x -> x.getItemPrice().multiply(BigDecimal.valueOf(x.getQuantity())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            if(orderRequest.getTotalPrice().compareTo(itemTotalPrice) != 0)
                throw new InvalidOrderException("Invalid total price !");
            // create order and orderItems
            Order order = orderRepository.save(Order.builder()
                    .orderStatus(OrderStatus.CREATED)
                    .customerId(orderRequest.getCustomerID())
                    .customerUserName(orderRequest.getCustomerUserName())
                    .transactionId(UUID.randomUUID())
                    .total(orderRequest.getTotalPrice())
                    .paymentId(orderRequest.getPaymentId())
                    .createdAt(LocalDate.now())
                    .build());
            for (OrderItemDto orderItemDto : orderRequest.getOrderItemDtoList()) {
                orderItemRepository.save(OrderItem.builder()
                        .productId(orderItemDto.getProductId())
                        .orderId(order.getId())
                        .quantity(orderItemDto.getQuantity())
                        .itemPrice(orderItemDto.getItemPrice())
                        .createdAt(LocalDate.now())
                        .build());
            }
            // send kafka message to inventory service
            OrderCreatedMessage orderCreatedMessage = orderMapper.prepareOrderMessage(order,orderRequest);
            kafkaTemplate.send(KAFKA_ORDER_CREATED_TOPIC_NAME,orderCreatedMessage);
            return ResponseEntity.status(HttpStatus.CREATED).body(order);
        }catch (InvalidOrderException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            log.error("Faild to create order: "+ ex.getMessage());
            return ResponseEntity.internalServerError().body("Failed to create order!");
        }

    }


}
