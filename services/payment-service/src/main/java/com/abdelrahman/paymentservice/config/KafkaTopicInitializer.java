package com.abdelrahman.paymentservice.config;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.errors.TopicExistsException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Properties;
import static com.abdelrahman.paymentservice.constant.Constant.KafkaConstant.*;


@Component
public class KafkaTopicInitializer implements ApplicationListener<ContextRefreshedEvent> {




    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        createTopicIfNotExists();
    }

    private void createTopicIfNotExists() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", BOOTSTRAP_SERVERS_URL);

        // create product topic and inventory topic in kafka if they are not already exist
        try (AdminClient adminClient = AdminClient.create(properties)) {
            NewTopic paymentTopic = TopicBuilder.name(KAFKA_ORDER_PAYMENT_TOPIC_NAME)
                    .partitions(1)
                    .replicas(1)
                    .build();

            adminClient.createTopics(Arrays.asList(paymentTopic)).all().get();
            System.out.println("Topics created successfully.");
        } catch (Exception e) {
            if (e.getCause() instanceof TopicExistsException) {
                System.out.println("Topic already exists.");
            } else {
                throw new RuntimeException("Error creating topic", e);
            }
        }
    }
}