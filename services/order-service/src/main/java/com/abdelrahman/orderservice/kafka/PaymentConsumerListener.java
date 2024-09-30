package com.abdelrahman.orderservice.kafka;

import com.abdelrahman.orderservice.client.EmailNotifyClient;
import com.abdelrahman.orderservice.client.InventoryClient;
import com.abdelrahman.orderservice.dto.kafka.*;
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
    private final EmailNotifyClient emailNotifyClient;
    private final InventoryClient inventoryClient;

    @KafkaListener(topics = KAFKA_ORDER_PAYMENT_TOPIC_NAME,groupId = ORDER_GROUP_ID,containerFactory = "orderPaymentKafkaListenerFactory")
    public void handlePaymentMessage(OrderPaymentMessage orderPaymentMessage){
        if(orderPaymentMessage.getPaymentStatus().equals(PaymentStatus.FAILED)){
            orderPaymentFailure(orderPaymentMessage.getOrderCreatedMessage(),orderPaymentMessage.getErrorMessage());
        }else {
            orderPaymentSuccess(orderPaymentMessage.getOrderCreatedMessage(),orderPaymentMessage.getPaymentStatus());
        }
    }

    @KafkaListener(topics = KAFKA_INVENTORY_FAILED_TOPIC_NAME,groupId = ORDER_GROUP_ID,containerFactory = "orderReservationFailureKafkaListenerFactory")
    public void handleInventoryFailedMessage(OrderReservationFailureMessage orderReservationFailureMessage){

            orderReservationFailed(orderReservationFailureMessage.getOrderCreatedMessage(),orderReservationFailureMessage.getIsDeducted(),orderReservationFailureMessage.getErrorMessage());

    }

    private void orderPaymentSuccess(OrderCreatedMessage orderMessage,PaymentStatus paymentStatus) {

        Order order = orderRepository.findById(orderMessage.getOrderId()).get();
        if (order.getOrderStatus().equals(OrderStatus.CREATED)) {
            order.setOrderStatus(OrderStatus.WAITING_FOR_DELIVERY);
            order.setPaymentStatus(paymentStatus);
            orderRepository.save(order);
            // send email to user order completed
            CompletedOrderEmailRequest completedOrderEmailRequest = CompletedOrderEmailRequest.builder()
                    .customerId(orderMessage.getCustomerId())
                    .customerUserName(orderMessage.getCustomerUserName())
                    .total(orderMessage.getTotal())
                    .orderItemDtoList(orderMessage.getOrderItemDtoList())
                    .build();
            emailNotifyClient.sendCompletedOrderEmail(completedOrderEmailRequest);
        }
    }

    private void orderPaymentFailure(OrderCreatedMessage orderMessage,String errorReason){
        Order order = orderRepository.findById(orderMessage.getOrderId()).get();
        if (order.getOrderStatus().equals(OrderStatus.CREATED)) {
            order.setOrderStatus(OrderStatus.CANCELED);
            order.setPaymentStatus(PaymentStatus.FAILED);
            orderRepository.save(order);
            // update order products inventory
            CancelOrderInventoryDeductionRequest deductionRequest = CancelOrderInventoryDeductionRequest.builder()
                    .orderItemDtoList(orderMessage.getOrderItemDtoList())
                    .build();
            inventoryClient.cancelOrderProductInventoryDeduction(deductionRequest);
            // send email to user order completed
            OrderFailedRequest orderFailedRequest =OrderFailedRequest.builder()
                    .customerUserName(orderMessage.getCustomerUserName())
                    .total(orderMessage.getTotal())
                    .errorMessage(errorReason)
                    .build();
            emailNotifyClient.sendFailedOrderEmail(orderFailedRequest);
        }
    }


    private void orderReservationFailed(OrderCreatedMessage orderMessage, Boolean deductStatus, String errorReason) {
        Order order = orderRepository.findById(orderMessage.getOrderId()).get();
        if (order.getOrderStatus().equals(OrderStatus.CREATED)) {
            order.setOrderStatus(OrderStatus.CANCELED);
            orderRepository.save(order);
            // update order products inventory
            CancelOrderInventoryDeductionRequest deductionRequest = CancelOrderInventoryDeductionRequest.builder()
                    .orderItemDtoList(orderMessage.getOrderItemDtoList())
                    .build();
            if (deductStatus)
                inventoryClient.cancelOrderProductInventoryDeduction(deductionRequest);
            // send email to user order completed
            OrderFailedRequest orderFailedRequest = OrderFailedRequest.builder()
                    .customerUserName(orderMessage.getCustomerUserName())
                    .total(orderMessage.getTotal())
                    .errorMessage(errorReason)
                    .build();
            emailNotifyClient.sendFailedOrderEmail(orderFailedRequest);
        }
    }

}
