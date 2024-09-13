package com.abdelrahman.paymentservice.service;

import com.abdelrahman.paymentservice.dto.kafka.CustomerPaymentDto;
import com.abdelrahman.paymentservice.entity.CustomerPayment;
import com.abdelrahman.paymentservice.exception.EntityNotFound;
import com.abdelrahman.paymentservice.mapper.CustomerPaymentMapper;
import com.abdelrahman.paymentservice.repository.CustomerPaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerPaymentService {
    private final CustomerPaymentRepository customerPaymentRepository;
    private final CustomerPaymentMapper customerPaymentMapper;


    @Transactional
    public ResponseEntity<String> addCustomerPayment(CustomerPaymentDto customerPaymentDto) {
        try {
            CustomerPayment customerPayment = customerPaymentMapper.convertDtoToEntity(customerPaymentDto);
            customerPayment = customerPaymentRepository.save(customerPayment);
            return ResponseEntity.ok().body("Customer payment added successfully");

        }catch (Exception ex){
            return ResponseEntity.internalServerError().body("Failed to add customer payment");
        }
    }

    public ResponseEntity<String> updateCustomerPayment(String customerPaymentId,CustomerPaymentDto customerPaymentDto) {
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

    public ResponseEntity<?> getCustomerPayment(String customerPaymentId) {
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
