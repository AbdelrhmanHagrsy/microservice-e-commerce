package com.abdelrahaman.authenticationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAuthDto {
    private Integer Id;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String telephone;
    private Boolean isAdmin;
}
