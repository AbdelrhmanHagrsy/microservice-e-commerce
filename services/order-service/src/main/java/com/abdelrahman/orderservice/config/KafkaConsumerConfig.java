package com.abdelrahman.orderservice.config;

import com.abdelrahman.orderservice.dto.kafka.OrderCreatedMessage;
import com.abdelrahman.orderservice.dto.kafka.OrderPaymentMessage;
import com.abdelrahman.orderservice.dto.kafka.OrderReservationFailureMessage;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

import static com.abdelrahman.orderservice.constant.Constant.KafkaConst.*;


@Configuration
public class KafkaConsumerConfig {

//    @Bean
//    public ConsumerFactory<String, String> consumerFactory() {
//        Map<String, Object> config = new HashMap<>();
//
//        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS_URL);
//        config.put(ConsumerConfig.GROUP_ID_CONFIG, ORDER_GROUP_ID);
//        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        return new DefaultKafkaConsumerFactory<>(config);
//    }
//
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory();
//        factory.setConsumerFactory(consumerFactory());
//        return factory;
//    }


    // handle Reservation Failure message which come from inventory-service
    @Bean
    public ConsumerFactory<String, OrderReservationFailureMessage> orderReservationFailureConsumerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS_URL);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, ORDER_GROUP_ID);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        config.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class.getName());
        config.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        config.put(JsonDeserializer.VALUE_DEFAULT_TYPE, OrderCreatedMessage.class.getName());
        config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(),
                new JsonDeserializer<>(OrderReservationFailureMessage.class));
    }


    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, OrderReservationFailureMessage> orderReservationFailureKafkaListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, OrderReservationFailureMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(orderReservationFailureConsumerFactory());
        return factory;
    }

    // handle Payment message which come from payment-service
    @Bean
    public ConsumerFactory<String, OrderPaymentMessage> orderPaymentConsumerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS_URL);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, ORDER_GROUP_ID);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        config.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class.getName());
        config.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        config.put(JsonDeserializer.VALUE_DEFAULT_TYPE, OrderCreatedMessage.class.getName());
        config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(),
                new JsonDeserializer<>(OrderPaymentMessage.class));
    }


    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, OrderPaymentMessage> orderPaymentKafkaListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, OrderPaymentMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(orderPaymentConsumerFactory());
        return factory;
    }
}
