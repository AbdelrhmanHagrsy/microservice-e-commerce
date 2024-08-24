package com.abdelrahman.customerservice.listeners;

import com.abdelrahman.customerservice.kafka.CustomerCreationRequest;
import com.abdelrahman.customerservice.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class customerCreationTopic {

    private final CustomerService customerService;

//    @KafkaListener(topics = {"${spring.kafka.topic.name}"},groupId = "${spring.kafka.consumer.group-id}")
//    public void customerCreationTopicListener(final CustomerCreationRequest customerCreationRequest){
//        log.info("starting to add new customer by kafka");
//        customerService.createCustomer(customerCreationRequest);
//        log.info("customer added by kakfa");
//
//    }


    @KafkaListener(topics = "customerProfileCreation", groupId = "customerGroup")
    public void listen(CustomerCreationRequest customerCreationRequest) {
        System.out.println("Received Message: " + customerCreationRequest.firstName());
    }
}
