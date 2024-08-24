package com.abdelrahaman.authenticationservice.mapper;

import com.abdelrahaman.authenticationservice.dto.RegistrationRequest;
import com.abdelrahaman.authenticationservice.entity.User;
import com.abdelrahaman.authenticationservice.kafka.CustomerCreationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthMapper {

    private final PasswordEncoder passwordEncoder;
    public User convertRegisRequestToRegsEntity(RegistrationRequest registrationRequest){
        return User.builder()
                .firstName(registrationRequest.firstName())
                .lastName(registrationRequest.lastName())
                .userName(registrationRequest.userName())
                .password(passwordEncoder.encode(registrationRequest.password()))
                .telephone(registrationRequest.telephone())
                .build();
    }

    public CustomerCreationRequest convertRegisRequestToCustomerRequest(RegistrationRequest registrationRequest){
        return CustomerCreationRequest.builder()
                .userName(registrationRequest.userName())
                .firstName(registrationRequest.firstName())
                .lastName(registrationRequest.lastName())
                .telephone(registrationRequest.telephone())
                .build();
    }

}
