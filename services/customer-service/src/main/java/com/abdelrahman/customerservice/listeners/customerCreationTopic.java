package com.abdelrahman.customerservice.listeners;

import com.abdelrahman.customerservice.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.abdelrhman.common.kafka.CustomerCreationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class customerCreationTopic {

    @Autowired
    private  CustomerService customerService;


    @KafkaListener(topics = {"${spring.kafka.topic.name}"},groupId = "${spring.kafka.consumer.group-id}")
    public void customerCreationTopicListener(final CustomerCreationRequest customerCreationRequest){
        log.info("starting to add new customer by kafka");
        customerService.createCustomer(customerCreationRequest);
        log.info("customer added by kakfa");

    }

}
