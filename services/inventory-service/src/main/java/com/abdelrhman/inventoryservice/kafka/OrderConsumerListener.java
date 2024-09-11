package com.abdelrhman.inventoryservice.kafka;

import com.abdelrhman.inventoryservice.dto.OrderItemDto;
import com.abdelrhman.inventoryservice.dto.kafka.InventoryReservedMessage;
import com.abdelrhman.inventoryservice.dto.kafka.OrderCreatedMessage;
import com.abdelrhman.inventoryservice.entity.ProductInventory;
import com.abdelrhman.inventoryservice.exception.QuantityNotAvailable;
import com.abdelrhman.inventoryservice.repository.ProductInventoryRepository;
import com.abdelrhman.inventoryservice.service.ProductInventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static com.abdelrhman.inventoryservice.constant.Constant.KafkaConst.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderConsumerListener {

    private final KafkaTemplate<String, OrderCreatedMessage> kafkaTemplate;
    private final ProductInventoryService productInventoryService;

    private Set<String> processedTransaction = ConcurrentHashMap.newKeySet();

    @KafkaListener(topics = KAFKA_ORDER_CREATED_TOPIC_NAME,groupId = INVENTORY_GROUP_ID,containerFactory = "orderCreatedKafkaListenerFactory")
    public void checkOrderProductsInventory(OrderCreatedMessage orderCreatedMessage)
    {
        log.info("Start checking order {} product quantity",orderCreatedMessage.getOrderId());

        // skip message if it already consumed
        if(processedTransaction.contains(orderCreatedMessage.getTransactionId())) {
            log.info("Order with transaction ID {} has already been processed", orderCreatedMessage.getTransactionId());
            return;
        }

        try {
            productInventoryService.deductOrderQuantityFromInventory(orderCreatedMessage);
            processedTransaction.add(orderCreatedMessage.getOrderId());
            // send message to payment service
            kafkaTemplate.send(KAFKA_INVENTORY_REVERSED_TOPIC_NAME,orderCreatedMessage);
        }catch (Exception e){
            log.error("Error while deducting quantity for order id :"+ orderCreatedMessage.getOrderId()+" :" + e.getMessage());
            // Send message to order service to Failed order topic
            orderCreatedMessage.setExceptionMessage(e.getMessage());
            kafkaTemplate.send(KAFKA_INVENTORY_FAILED_TOPIC_NAME,orderCreatedMessage);
        }

    }
}
