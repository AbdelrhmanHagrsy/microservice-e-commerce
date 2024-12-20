package com.abdelrahman.orderservice.service;

import com.abdelrahman.orderservice.dto.kafka.*;
import com.abdelrahman.orderservice.entity.Order;
import com.abdelrahman.orderservice.entity.OrderItem;
import com.abdelrahman.orderservice.exception.*;
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

    public String markOrderAsDelivered(String orderId) {
        try {
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new EntityNotFound("Order not exist!"));

            // validate order status before paid order
            if(order.getOrderStatus().equals(OrderStatus.CANCELED))
                throw new OrderDeliveredFailed("Cannot mark order as delivered: The order has been canceled and cannot be processed.");

            if (order.getDeliveryStatus().equals(DeliveryStatus.DELIVERED)) {
                throw new OrderDeliveredFailed("Order already delivered");
            }
            if(! order.getPaymentStatus().equals(PaymentStatus.COMPLETED))
                throw new OrderDeliveredFailed("Order should get paid first");

            order.setDeliveryStatus(DeliveryStatus.DELIVERED);
            order.setOrderStatus(OrderStatus.COMPLETED);
            orderRepository.save(order);
            return "Order is completed successfully";
        }catch (OrderDeliveredFailed e){
            throw e;
        }
        catch (EntityNotFound e) {
            log.error(e.getMessage());
            throw e;
        }

    }
    public String markOrderAsPaid(String orderId) {
        try {
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new EntityNotFound("Order not exist!"));

            // validate order status before paid order
            if(order.getOrderStatus().equals(OrderStatus.CANCELED))
                throw new OrderPaidFailed("Cannot mark order as paid: The order has been canceled and cannot be processed.");
            if(order.getPaymentStatus().equals(PaymentStatus.COMPLETED))
                throw new OrderPaidFailed("Cannot mark order as paid: The order has already been paid.");

            order.setOrderStatus(OrderStatus.COMPLETED);
            orderRepository.save(order);
            return "Order marked as paid successfully.";
        }catch (OrderPaidFailed e){
            throw e;
        }
        catch (EntityNotFound e) {
            log.error(e.getMessage());
            throw e;
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
    public List<Order> fetchAllCompletedOrdersByUserEmail(String userName) {
        return orderRepository.findAllByCustomerUserNameAndOrderStatus(userName,OrderStatus.COMPLETED);
    }



}
