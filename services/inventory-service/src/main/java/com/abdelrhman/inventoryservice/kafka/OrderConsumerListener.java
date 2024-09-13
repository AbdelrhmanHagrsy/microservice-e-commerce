package com.abdelrhman.inventoryservice.kafka;


import com.abdelrhman.inventoryservice.dto.kafka.OrderReservationFailureMessage;
import com.abdelrhman.inventoryservice.dto.kafka.ReservedInventoryOrderMessage;
import com.abdelrhman.inventoryservice.repository.OrderTransactionRepository;
import com.abdelrhman.inventoryservice.dto.kafka.OrderCreatedMessage;
import com.abdelrhman.inventoryservice.entity.OrderTransaction;
import com.abdelrhman.inventoryservice.service.ProductInventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.abdelrhman.inventoryservice.constant.Constant.KafkaConst.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderConsumerListener {

    private final KafkaTemplate<String, ReservedInventoryOrderMessage> reservedMessageKafkaTemplate;
    private final KafkaTemplate<String, OrderReservationFailureMessage> orderReservationFailureMessageKafkaTemplate;

    private final ProductInventoryService productInventoryService;

    private final OrderTransactionRepository orderTransactionRepository;

    @KafkaListener(topics = KAFKA_ORDER_CREATED_TOPIC_NAME, groupId = INVENTORY_GROUP_ID, containerFactory = "orderCreatedKafkaListenerFactory")
    public void handleOrderProductReservation(OrderCreatedMessage orderCreatedMessage) {
        log.info("Start checking order {} product quantity", orderCreatedMessage.getOrderId());

        Optional<OrderTransaction> optionalOrderTransaction = orderTransactionRepository.findById(orderCreatedMessage.getTransactionId());
        OrderTransaction  orderTransaction;
        if (optionalOrderTransaction.isEmpty()) {
            orderTransaction = orderTransactionRepository.save(
                    OrderTransaction.builder()
                            .id(orderCreatedMessage.getTransactionId())
                            .isDeducted(false)
                            .sendToPayment(false)
                            .build());
        } else {
            orderTransaction = optionalOrderTransaction.get();
            // skip if order already deducted and payed
            if (optionalOrderTransaction.get().getIsDeducted() && optionalOrderTransaction.get().getSendToPayment())
                return;
        }

        try {
            // Deduct from product inventory
            if (!orderTransaction.getIsDeducted()) {
                productInventoryService.deductOrderQuantityFromInventory(orderCreatedMessage);
                orderTransaction.setIsDeducted(true);
                orderTransactionRepository.save(orderTransaction);
            }
            // Send order message to payment service
            sendToPaymentService(orderTransaction, orderCreatedMessage);
        } catch (Exception e) {
            log.error("Error while deducting quantity for order id {} : {}", orderCreatedMessage.getOrderId(), e.getMessage());
            // send message to order service Reserved is Failed
            OrderTransaction transaction = orderTransactionRepository.findById(orderCreatedMessage.getTransactionId()).get();
            OrderReservationFailureMessage orderReservationFailureMessage = OrderReservationFailureMessage.builder()
                    .orderCreatedMessage(orderCreatedMessage)
                    .isDeducted(transaction.getIsDeducted())
                    .errorMessage(e.getMessage())
                    .build();
            orderReservationFailureMessageKafkaTemplate.send(KAFKA_INVENTORY_FAILED_TOPIC_NAME, orderReservationFailureMessage);
        }
    }


    private void sendToPaymentService(OrderTransaction orderTransaction, OrderCreatedMessage orderCreatedMessage) {
        try {
            ReservedInventoryOrderMessage reservedInventoryOrderMessage = ReservedInventoryOrderMessage.builder()
                    .orderCreatedMessage(orderCreatedMessage)
                    .isDeducted(true)
                    .build();
            // send message to payment service
            reservedMessageKafkaTemplate.send(KAFKA_INVENTORY_RESERVATION_MESSAGES_TOPIC, reservedInventoryOrderMessage);
            orderTransaction.setSendToPayment(true);
            orderTransactionRepository.save(orderTransaction);
        } catch (Exception e) {
            log.error("Error sending message to payment service for order id {}: {}", orderCreatedMessage.getOrderId(), e.getMessage());
            throw new RuntimeException("Error sending message to payment service : " + e.getMessage());
        }
    }

}
