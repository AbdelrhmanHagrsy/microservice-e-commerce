package com.abdelrahman.customerservice.controller;

import com.abdelrahman.customerservice.dto.CustomerAddressDto;
import com.abdelrahman.customerservice.entity.CustomerAddress;
import com.abdelrahman.customerservice.service.CustomerAddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.abdelrahman.customerservice.constant.Constant.CustomerAddressController.*;

@RestController
@RequestMapping(CUSTOMER_ADDRESS_CONTROLLER)
@RequiredArgsConstructor
@Slf4j
public class CustomerAddressController {

    private final CustomerAddressService customerAddressService;

    @PostMapping(ADD_CUSTOMER_ADDRESS)
    public ResponseEntity<String> addCustomerAddress(@PathVariable(name = "customer_id") Long customerId, @RequestBody CustomerAddressDto customerAddressDto){
        return customerAddressService.addCustomerAddress(customerId,customerAddressDto);
    }

    @PutMapping(UPDATE_CUSTOMER_ADDRESS)
    public ResponseEntity<String> updateCustomerAddress(@PathVariable(name = "customerAddress_id") Long customerAddressId, @RequestBody CustomerAddressDto customerAddressDto){
        return customerAddressService.updateCustomerAddress(customerAddressId,customerAddressDto);
    }

    @GetMapping(GET_CUSTOMER_ADDRESS)
    public ResponseEntity<?> getCustomerAddress(@PathVariable(name = "customerAddress_id") Long customerAddressId){
        return customerAddressService.getCustomerAddress(customerAddressId);
    }
}
