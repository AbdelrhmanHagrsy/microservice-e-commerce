package com.abdelrahman.customerservice.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CustomerAddressDto {
    private Long id;
    private String addressLineOne;
    private String addressLineTwo;
    private String city;
    private String postalCode;
    private String country;
    private String telephone;
    private String mobile;
}
