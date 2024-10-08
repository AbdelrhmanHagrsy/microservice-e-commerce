package com.abdelrahaman.authenticationservice.entity;

import com.abdelrahaman.authenticationservice.dto.UserRoles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document
public class User {
    @Id
    private String id;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String telephone;
    private Boolean isActive;
    private UserRoles userRoles;
}
