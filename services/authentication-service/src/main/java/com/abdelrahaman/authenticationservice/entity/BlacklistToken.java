package com.abdelrahaman.authenticationservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document
public class BlacklistToken {
    @org.springframework.data.annotation.Id
    private String Id;
    private String userName;
    private String jwtToken;
}
