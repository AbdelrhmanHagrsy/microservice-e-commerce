package com.abdelrahman.orderservice.service;

import com.abdelrahman.orderservice.dto.kafka.*;
import com.abdelrahman.orderservice.entity.Order;
import com.abdelrahman.orderservice.entity.OrderItem;
import com.abdelrahman.orderservice.exception.InvalidOrderException;
import com.abdelrahman.orderservice.exception.OrderConflictExceotion;
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
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.abdelrahman.orderservice.constant.Constant.KafkaConst.KAFKA_ORDER_CREATED_TOPIC_NAME;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderMapper orderMapper;

    private final RedisService redisService;
    private final KafkaTemplate<String, OrderCreatedMessage> kafkaTemplate;


    @Transactional
    public String createOrder(OrderRequest orderRequest) {

        try {
            // handel idempotency te prevent duplicate same order request
            // add idempotent key in redis for 5 min
            boolean status = redisService.addOrderLock(orderRequest.getIdempotentKey(), 300L);
            if (!status) {
                throw new OrderConflictExceotion("Order already processed");
            }
            // validate total price
            BigDecimal itemTotalPrice = orderRequest.getOrderItemDtoList().stream()
                    .map(x -> x.getItemPrice().multiply(BigDecimal.valueOf(x.getQuantity())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            if (orderRequest.getTotalPrice().compareTo(itemTotalPrice) != 0)
                throw new InvalidOrderException("Invalid total price !");
            // create order and orderItems
            Order order = orderRepository.save(Order.builder()
                    .orderStatus(OrderStatus.CREATED)
                    .paymentStatus(PaymentStatus.PENDING)
                    .deliveryStatus(DeliveryStatus.NOT_DISPATCHED)
                    .customerId(orderRequest.getCustomerID())
                    .customerUserName(orderRequest.getCustomerUserName())
                    .transactionId(UUID.randomUUID())
                    .total(orderRequest.getTotalPrice())
                    .paymentId(orderRequest.getPaymentId())
                    .createdAt(LocalDate.now())
                    .build());
            //
            for (OrderItemDto orderItemDto : orderRequest.getOrderItemDtoList()) {
                orderItemRepository.save(OrderItem.builder()
                        .productId(orderItemDto.getProductId())
                        .orderId(order.getId())
                        .productName(orderItemDto.getProductName())
                        .quantity(orderItemDto.getQuantity())
                        .itemPrice(orderItemDto.getItemPrice())
                        .createdAt(LocalDate.now())
                        .build());
            }
            // send kafka message to inventory service
            OrderCreatedMessage orderCreatedMessage = orderMapper.prepareOrderMessage(order, orderRequest);
            kafkaTemplate.send(KAFKA_ORDER_CREATED_TOPIC_NAME, orderCreatedMessage);
            return "Order under processing";
        } catch (InvalidOrderException ex) {
            log.error(ex.getMessage());
            throw ex;
        } catch (OrderConflictExceotion ex) {
            log.error(ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Faild to create order: " + ex.getMessage());
            throw ex;
        } finally {
            redisService.removeIdempotentKey(orderRequest.getIdempotentKey());
        }

    }


    public List<Order> fetchAllOrdersByUserEmail(String userName) {
        List<Order> orderList = orderRepository.findAllByCustomerUserName(userName);
        // remove all canceled orders
        orderList = orderList.stream().filter(x-> ! x.getOrderStatus().equals(OrderStatus.CANCELED)).collect(Collectors.toList());
        return orderList;
    }
    public List<Order> fetchAllCanceledOrdersByUserEmail(String userName) {
        return orderRepository.findAllByCustomerUserNameAndOrderStatus(userName,OrderStatus.CANCELED);
    }

}
