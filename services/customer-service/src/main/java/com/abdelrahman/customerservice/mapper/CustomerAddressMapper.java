package com.abdelrahman.customerservice.mapper;

import com.abdelrahman.customerservice.dto.CustomerAddressDto;
import com.abdelrahman.customerservice.entity.CustomerAddress;
import org.springframework.stereotype.Component;

@Component
public class CustomerAddressMapper {

    public CustomerAddress convertDtoToEntity(CustomerAddressDto customerAddressDto){
        return CustomerAddress.builder()
                .id(customerAddressDto.getId())
                .addressLineOne(customerAddressDto.getAddressLineOne())
                .addressLineTwo(customerAddressDto.getAddressLineTwo())
                .city(customerAddressDto.getCity())
                .country(customerAddressDto.getCountry())
                .mobile(customerAddressDto.getMobile())
                .postalCode(customerAddressDto.getPostalCode())
                .telephone(customerAddressDto.getTelephone())
                .build();
    }

    public CustomerAddressDto convertEntityToDto(CustomerAddress customerAddress){
        return CustomerAddressDto.builder()
                .id(customerAddress.id)
                .addressLineOne(customerAddress.getAddressLineOne())
                .addressLineTwo(customerAddress.getAddressLineTwo())
                .mobile(customerAddress.getMobile())
                .city(customerAddress.getCity())
                .country(customerAddress.getCountry())
                .telephone(customerAddress.getTelephone())
                .postalCode(customerAddress.getPostalCode())
                .build();
    }
}
