package com.abdelrahman.customerservice.controller;

import com.abdelrahman.customerservice.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.abdelrahman.customerservice.constant.Constant.CustomerController.*;

@RestController
@RequestMapping(CUSTOMER_CONTROLLER)
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping(GET_CUSTOMER)
    public ResponseEntity<?> getCustomer(@PathVariable(name = "customer_id") Long customerID){
        return customerService.getCustomer(customerID);
    }

}
