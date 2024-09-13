package com.abdelrahman.paymentservice.mapper;


import com.abdelrahman.paymentservice.dto.kafka.CustomerPaymentDto;
import com.abdelrahman.paymentservice.entity.CustomerPayment;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Component
public class CustomerPaymentMapper {


  public CustomerPayment convertDtoToEntity(CustomerPaymentDto customerPaymentDto) throws ParseException {
      return CustomerPayment.builder()
              .id(customerPaymentDto.getId())
              .customerId(customerPaymentDto.getCustomerId())
              .paymentType(customerPaymentDto.getPaymentType())
              .expiry(new SimpleDateFormat("dd/MM/yyyy").parse(customerPaymentDto.getExpiry()))
              .provider(customerPaymentDto.getProvider())
              .accountNo(customerPaymentDto.getAccountNo())
              .build();
  }

  public CustomerPaymentDto convertEntityToDto(CustomerPayment customerPayment){
      return CustomerPaymentDto.builder()
              .id(customerPayment.getId())
              .customerId(customerPayment.getCustomerId())
              .accountNo(customerPayment.getAccountNo())
              .expiry(customerPayment.getExpiry().toString())
              .provider(customerPayment.getProvider())
              .paymentType(customerPayment.getPaymentType())
              .build();
  }
}
