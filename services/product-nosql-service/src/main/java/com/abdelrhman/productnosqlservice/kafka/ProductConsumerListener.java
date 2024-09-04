package com.abdelrhman.productnosqlservice.kafka;

import com.abdelrhman.productnosqlservice.ProductService;
import com.abdelrhman.productnosqlservice.dto.ProductMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.abdelrhman.productnosqlservice.constant.Constant.KafkaConstant.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductConsumerListener {

    private final ProductService productService;

    @KafkaListener(topics = KAFKA_PRODUCT_TOPIC_NAME,groupId = PRODUCT_GROUP_ID,containerFactory = "productKafkaListenerFactory")
    public void productListener(ProductMessage productMessage){
        log.info("Kafka consumer received a new product message");
        productService.addProduct(productMessage);
    }
}
