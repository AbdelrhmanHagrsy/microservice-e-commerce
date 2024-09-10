package com.abdelrahman.customerservice.mapper;

import com.abdelrahman.customerservice.dto.CustomerPaymentDto;
import com.abdelrahman.customerservice.entity.CustomerPayment;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Component
public class CustomerPaymentMapper {


  public CustomerPayment convertDtoToEntity(CustomerPaymentDto customerPaymentDto) throws ParseException {
      return CustomerPayment.builder()
              .id(customerPaymentDto.getId())
              .paymentType(customerPaymentDto.getPaymentType())
              .expiry(new SimpleDateFormat("dd/MM/yyyy").parse(customerPaymentDto.getExpiry()))
              .provider(customerPaymentDto.getProvider())
              .accountNo(customerPaymentDto.getAccountNo())
              .build();
  }

  public CustomerPaymentDto convertEntityToDto(CustomerPayment customerPayment){
      return CustomerPaymentDto.builder()
              .id(customerPayment.id)
              .accountNo(customerPayment.getAccountNo())
              .expiry(customerPayment.getExpiry().toString())
              .provider(customerPayment.getProvider())
              .paymentType(customerPayment.getPaymentType())
              .build();
  }
}
