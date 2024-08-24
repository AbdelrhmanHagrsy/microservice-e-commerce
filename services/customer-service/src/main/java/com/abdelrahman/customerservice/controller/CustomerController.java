package com.abdelrahman.customerservice.controller;

import com.abdelrahman.customerservice.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.abdelrahman.customerservice.constant.Constant.ControllersMapping.CUSTOMER_CONTROLLER;

@RestController
@RequestMapping(CUSTOMER_CONTROLLER)
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

//
//    @PostMapping(REGISTER_NEW_USER_API)
//    public ResponseEntity<String> createCustomer(@RequestBody RegistrationRequest registrationRequest){
//
//    }

}
