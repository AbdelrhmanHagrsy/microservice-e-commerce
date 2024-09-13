package com.abdelrahman.paymentservice.config;

import com.abdelrahman.paymentservice.dto.kafka.OrderPaymentMessage;
import org.apache.kafka.clients.admin.AdminClientConfig;
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

import static com.abdelrahman.paymentservice.constant.Constant.KafkaConstant.BOOTSTRAP_SERVERS_URL;

@EnableKafka
@Configuration
public class KafkaProducerConfig {



    @Bean
    public ProducerFactory<String, OrderPaymentMessage> orderPaymentKafkaFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS_URL);
        config.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        config.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false); // Optional: If you want to handle type info manually
        return new DefaultKafkaProducerFactory<>(config);
    }


    @Bean
    public KafkaTemplate<String, OrderPaymentMessage> orderPaymentKafkaTemplate() {
        return new KafkaTemplate<>(orderPaymentKafkaFactory());
    }


    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> config = new HashMap<>();
        config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS_URL);
        return new KafkaAdmin(config);
    }

}