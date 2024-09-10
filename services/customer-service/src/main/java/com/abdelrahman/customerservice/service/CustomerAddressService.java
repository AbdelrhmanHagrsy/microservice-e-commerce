package com.abdelrahman.customerservice.service;

import com.abdelrahman.customerservice.dto.CustomerAddressDto;
import com.abdelrahman.customerservice.entity.Customer;
import com.abdelrahman.customerservice.entity.CustomerAddress;
import com.abdelrahman.customerservice.exception.EntityNotFound;
import com.abdelrahman.customerservice.mapper.CustomerAddressMapper;
import com.abdelrahman.customerservice.repository.CustomerAddressRepository;
import com.abdelrahman.customerservice.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerAddressService {
    private final CustomerAddressRepository customerAddressRepository;
    private final CustomerRepository customerRepository;
    private final CustomerAddressMapper customerAddressMapper;


    public ResponseEntity<String> addCustomerAddress(Long customerId, CustomerAddressDto customerAddressDto) {
        try {
            Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new EntityNotFound(String.format("Customer with id: %s Not Found", customerId)));
            CustomerAddress customerAddress = customerAddressMapper.convertDtoToEntity(customerAddressDto);
            customer.setCustomerAddress(customerAddress);
            customerAddress.setCustomer(customer);
            customerAddressRepository.save(customerAddress);
            return ResponseEntity.ok().body("Customer address added successfully");
        }catch (EntityNotFound ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }catch (Exception ex){
            return ResponseEntity.internalServerError().body("Failed to add customer address");
        }
    }

    public ResponseEntity<String> updateCustomerAddress(Long customerAddressId,CustomerAddressDto customerAddressDto) {
        try {
            CustomerAddress customerAddress = customerAddressRepository.findById(customerAddressId)
                    .orElseThrow(() -> new EntityNotFound(String.format("Customer Address with id: %s Not Found", customerAddressId)));

            customerAddress = customerAddressMapper.convertDtoToEntity(customerAddressDto);
            customerAddress.setId(customerAddressId);
            customerAddressRepository.save(customerAddress);
            return ResponseEntity.ok().body("Customer address updated successfully");
        }catch (EntityNotFound ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }catch (Exception ex){
            return ResponseEntity.internalServerError().body("Failed to update customer address");
        }
    }

    public ResponseEntity<?> getCustomerAddress(Long customerAddressId) {
        try {
            CustomerAddress customerAddress = customerAddressRepository.findById(customerAddressId)
                    .orElseThrow(() -> new EntityNotFound(String.format("Customer Address with id: %s Not Found", customerAddressId)));

            return ResponseEntity.ok().body(customerAddressMapper.convertEntityToDto(customerAddress));
        }catch (EntityNotFound ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }catch (Exception ex){
            return ResponseEntity.internalServerError().body("Failed to update customer address");
        }
    }

}
