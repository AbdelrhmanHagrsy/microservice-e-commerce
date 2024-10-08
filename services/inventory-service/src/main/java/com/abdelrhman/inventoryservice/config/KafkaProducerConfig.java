package com.abdelrhman.inventoryservice.config;

import com.abdelrhman.inventoryservice.dto.kafka.*;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

import static com.abdelrhman.inventoryservice.constant.Constant.KafkaConst.BOOTSTRAP_SERVERS_URL;


@EnableKafka
@Configuration
public class KafkaProducerConfig {





    // Inventory Reserved Kafka Producer Configuration
    @Bean
    public ProducerFactory<String, ReservedInventoryOrderMessage> reservedInventoryOrderProducerFactory() {
        Map<String, Object> config = producerConfig();
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, ReservedInventoryOrderMessage> reservedInventoryOrderKafkaTemplate() {
        return new KafkaTemplate<>(reservedInventoryOrderProducerFactory());
    }

    // Inventory Failed Reserved Kafka Producer Configuration
    @Bean
    public ProducerFactory<String, OrderReservationFailureMessage> orderReservationFailureProducerFactory() {
        Map<String, Object> config = producerConfig();
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, OrderReservationFailureMessage> orderReservationFailureFailedKafkaTemplate() {
        return new KafkaTemplate<>(orderReservationFailureProducerFactory());
    }

    // Shared Producer Configuration
    private Map<String, Object> producerConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS_URL);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        config.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false); // Optional
        return config;
    }


    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> config = new HashMap<>();
        config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS_URL);
        return new KafkaAdmin(config);
    }

}