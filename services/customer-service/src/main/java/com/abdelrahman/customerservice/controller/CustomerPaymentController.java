package com.abdelrahman.customerservice.controller;

import com.abdelrahman.customerservice.dto.CustomerAddressDto;
import com.abdelrahman.customerservice.dto.CustomerPaymentDto;
import com.abdelrahman.customerservice.service.CustomerPaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.abdelrahman.customerservice.constant.Constant.CustomerPaymentController.*;

@RestController
@RequestMapping(CUSTOMER_PAYMENT_CONTROLLER)
@RequiredArgsConstructor
@Slf4j
public class CustomerPaymentController {

    private final CustomerPaymentService customerPaymentService;

    @PostMapping(ADD_CUSTOMER_PAYMENT)
    public ResponseEntity<String> addCustomerPayment(@PathVariable(name = "customer_id") Long customerId, @RequestBody CustomerPaymentDto customerPaymentDto){
        return customerPaymentService.addCustomerPayment(customerId,customerPaymentDto);
    }

    @PutMapping(UPDATE_CUSTOMER_PAYMENT)
    public ResponseEntity<String> updateCustomerPayment(@PathVariable(name = "customerPayment_id") Long customerAddressId, @RequestBody CustomerPaymentDto customerPaymentDto){
        return customerPaymentService.updateCustomerPayment(customerAddressId,customerPaymentDto);
    }

    @GetMapping(GET_CUSTOMER_PAYMENT)
    public ResponseEntity<?> getCustomerPayment(@PathVariable(name = "customerPayment_id") Long customerPaymentId){
        return customerPaymentService.getCustomerPayment(customerPaymentId);
    }
}
