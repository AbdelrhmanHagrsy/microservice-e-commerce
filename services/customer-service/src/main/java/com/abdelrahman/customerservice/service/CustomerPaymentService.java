package com.abdelrahman.customerservice.service;

import com.abdelrahman.customerservice.dto.CustomerAddressDto;
import com.abdelrahman.customerservice.dto.CustomerPaymentDto;
import com.abdelrahman.customerservice.entity.Customer;
import com.abdelrahman.customerservice.entity.CustomerAddress;
import com.abdelrahman.customerservice.entity.CustomerPayment;
import com.abdelrahman.customerservice.exception.EntityNotFound;
import com.abdelrahman.customerservice.mapper.CustomerPaymentMapper;
import com.abdelrahman.customerservice.repository.CustomerPaymentRepository;
import com.abdelrahman.customerservice.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerPaymentService {
    private final CustomerRepository customerRepository;
    private final CustomerPaymentRepository customerPaymentRepository;
    private final CustomerPaymentMapper customerPaymentMapper;


    @Transactional
    public ResponseEntity<String> addCustomerPayment(Long customerId, CustomerPaymentDto customerPaymentDto) {
        try {
            Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new EntityNotFound(String.format("Customer with id: %s Not Found", customerId)));
            CustomerPayment customerPayment = customerPaymentMapper.convertDtoToEntity(customerPaymentDto);
            customerPayment.setCustomer(customer);
            customerPayment = customerPaymentRepository.save(customerPayment);

            return ResponseEntity.ok().body("Customer payment added successfully");
        }catch (EntityNotFound ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }catch (Exception ex){
            return ResponseEntity.internalServerError().body("Failed to add customer payment");
        }
    }

    public ResponseEntity<String> updateCustomerPayment(Long customerPaymentId,CustomerPaymentDto customerPaymentDto) {
        try {
            CustomerPayment customerPayment = customerPaymentRepository.findById(customerPaymentId)
                    .orElseThrow(() -> new EntityNotFound(String.format("Customer Payment with id: %s Not Found", customerPaymentId)));

            customerPayment = customerPaymentMapper.convertDtoToEntity(customerPaymentDto);
            customerPayment.setId(customerPaymentId);
            customerPaymentRepository.save(customerPayment);
            return ResponseEntity.ok().body("Customer payment updated successfully");
        }catch (EntityNotFound ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }catch (Exception ex){
            return ResponseEntity.internalServerError().body("Failed to update customer payment");
        }
    }

    public ResponseEntity<?> getCustomerPayment(Long customerPaymentId) {
        try {
            CustomerPayment customerAddress = customerPaymentRepository.findById(customerPaymentId)
                    .orElseThrow(() -> new EntityNotFound(String.format("Customer Payment with id: %s Not Found", customerPaymentId)));

            return ResponseEntity.ok().body(customerPaymentMapper.convertEntityToDto(customerAddress));
        }catch (EntityNotFound ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }catch (Exception ex){
            return ResponseEntity.internalServerError().body("Failed to update customer payment");
        }
    }

}
