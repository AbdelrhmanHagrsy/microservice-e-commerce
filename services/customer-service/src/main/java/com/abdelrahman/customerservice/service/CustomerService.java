package com.abdelrahman.customerservice.service;

import com.abdelrahman.customerservice.entity.Customer;
import com.abdelrahman.customerservice.entity.CustomerPayment;
import com.abdelrahman.customerservice.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.abdelrhman.common.kafka.CustomerCreationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Set;

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

    public ResponseEntity<?> getCustomer(Long customerID) {
        Customer customer = customerRepository.findById(customerID).get();
        Set<CustomerPayment> customerPaymentSet = customerRepository.getCustomerPaymentByCustomerId(customerID);
        customer.setCustomerPaymentSet(customerPaymentSet);
        return ResponseEntity.ok().body(customer);
    }
}
