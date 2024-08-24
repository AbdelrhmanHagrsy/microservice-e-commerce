package com.abdelrahman.customerservice.service;

import com.abdelrahman.customerservice.kafka.CustomerCreationRequest;
import com.abdelrahman.customerservice.entity.Customer;
import com.abdelrahman.customerservice.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public void createCustomer(CustomerCreationRequest customerCreationRequest){
        Customer customer = Customer.builder()
                .userName(customerCreationRequest.userName())
                .firstName(customerCreationRequest.firstName())
                .lastName(customerCreationRequest.lastName())
                .telephone(customerCreationRequest.telephone())
                .build();
        customerRepository.save(customer);
    }

}
