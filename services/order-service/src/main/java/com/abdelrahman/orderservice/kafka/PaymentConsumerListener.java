package com.abdelrahman.orderservice.kafka;

import com.abdelrahman.orderservice.dto.kafka.OrderStatus;
import com.abdelrahman.orderservice.dto.kafka.OrderCreatedMessage;
import com.abdelrahman.orderservice.dto.kafka.OrderPaymentMessage;
import com.abdelrahman.orderservice.entity.Order;
import com.abdelrahman.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import static com.abdelrahman.orderservice.constant.Constant.KafkaConst.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class PaymentConsumerListener {

    private final OrderRepository orderRepository;

    @KafkaListener(topics = KAFKA_ORDER_PAYMENT_TOPIC_NAME,groupId = ORDER_GROUP_ID,containerFactory = "orderPaymentKafkaListenerFactory")
    public void handlePaymentMessage(OrderPaymentMessage orderPaymentMessage){
        if(orderPaymentMessage.getIsOrderPaid()){
            orderPaymentSuccess(orderPaymentMessage.getOrderCreatedMessage());
        }else {
            orderPaymentFailure(orderPaymentMessage.getOrderCreatedMessage());
        }
    }


    private void orderPaymentSuccess(OrderCreatedMessage orderMessage) {

        Order order = orderRepository.findById(orderMessage.getOrderId()).get();
        if (order.getOrderStatus().equals(OrderStatus.CREATED)) {
            order.setOrderStatus(OrderStatus.COMPLETED);

            orderRepository.save(order);
            // send email to user order completed
        }
    }

    private void orderPaymentFailure(OrderCreatedMessage orderMessage){
        Order order = orderRepository.findById(orderMessage.getOrderId()).get();
        if (order.getOrderStatus().equals(OrderStatus.CREATED)) {
            order.setOrderStatus(OrderStatus.FAILED);
            orderRepository.save(order);
            // update order products inventory

            // send email to user order completed
        }
    }
}
