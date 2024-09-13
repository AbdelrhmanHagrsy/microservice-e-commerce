package com.abdelrahman.paymentservice.controller;

import com.abdelrahman.paymentservice.dto.kafka.CustomerPaymentDto;
import com.abdelrahman.paymentservice.service.CustomerPaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.abdelrahman.paymentservice.constant.Constant.CustomerPaymentController.*;

@RestController
@RequestMapping(CUSTOMER_PAYMENT_CONTROLLER)
@RequiredArgsConstructor
@Slf4j
public class CustomerPaymentController {

    private final CustomerPaymentService customerPaymentService;

    @PostMapping(ADD_CUSTOMER_PAYMENT)
    public ResponseEntity<String> addCustomerPayment(@RequestBody CustomerPaymentDto customerPaymentDto){
        return customerPaymentService.addCustomerPayment(customerPaymentDto);
    }

    @PutMapping(UPDATE_CUSTOMER_PAYMENT)
    public ResponseEntity<String> updateCustomerPayment(@PathVariable(name = "customerPayment_id") String customerAddressId, @RequestBody CustomerPaymentDto customerPaymentDto){
        return customerPaymentService.updateCustomerPayment(customerAddressId,customerPaymentDto);
    }

    @GetMapping(GET_CUSTOMER_PAYMENT)
    public ResponseEntity<?> getCustomerPayment(@PathVariable(name = "customerPayment_id") String customerPaymentId){
        return customerPaymentService.getCustomerPayment(customerPaymentId);
    }
}
