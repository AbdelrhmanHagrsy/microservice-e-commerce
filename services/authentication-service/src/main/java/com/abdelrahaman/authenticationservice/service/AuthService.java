package com.abdelrahaman.authenticationservice.service;

import com.abdelrahaman.authenticationservice.client.EmailNotifClient;
import com.abdelrahaman.authenticationservice.dto.RegistirationEmailRequest;
import com.abdelrahaman.authenticationservice.dto.RegistrationRequest;
import com.abdelrahaman.authenticationservice.entity.ConfirmationToken;
import com.abdelrahaman.authenticationservice.entity.User;
import com.abdelrahaman.authenticationservice.exception.EntityAlreadyExist;
import com.abdelrahaman.authenticationservice.mapper.AuthMapper;
import com.abdelrahaman.authenticationservice.repository.ConfirmationTokenRepository;
import com.abdelrahaman.authenticationservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.abdelrhman.common.kafka.CustomerCreationRequest;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.Optional;
import static com.abdelrahaman.authenticationservice.constant.Constant.ApisMapping.CONFIRM_ACCOUNT_API;
import static com.abdelrahaman.authenticationservice.constant.Constant.ControllersMapping.USER_CONTROLLER;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final AuthMapper authMapper;
    private final EmailNotifClient emailNotifClient;
    private final KafkaTemplate<String, CustomerCreationRequest> kafkaTemplate;
   // private final NewTopic newTopic;
    @Value("${spring.kafka.topic.name}")
    private String customerTopicName;
    @Value("${getway.url}")
    private String getwayUrl;


    @Transactional
    public String RegisterNewUser(RegistrationRequest registrationRequest){
        Optional<User> user = userRepository.findByUserName(registrationRequest.userName());
        if(user.isPresent())
            throw new EntityAlreadyExist(String.format("User name : %s is already registered !",registrationRequest.userName()));
        //
        User newUser = authMapper.convertRegisRequestToRegsEntity(registrationRequest);
        newUser.setIsActive(false);
        User createdUser = userRepository.save(newUser);
        ConfirmationToken confirmationToken = confirmationTokenRepository.save(
                ConfirmationToken.builder()
                        .userId(createdUser.getId())
                        .CreaDate(new Date(System.currentTimeMillis()))
                        .build());
        // Send confirmation Email to user
        RegistirationEmailRequest registirationEmailRequest = prepareRegistirationEmail(confirmationToken.getId(),registrationRequest);
        sentEmail(registirationEmailRequest);
        // send a new Message to customer service to create a new customer profile
        CustomerCreationRequest customerCreationRequest = authMapper.convertRegisRequestToCustomerRequest(registrationRequest);
        kafkaTemplate.send(customerTopicName,customerCreationRequest);

        log.info("Confirmation Token with ID:"+confirmationToken.getId()+" Done");
        return "Verify email by the link sent on your email address";
    }

    private RegistirationEmailRequest prepareRegistirationEmail(String confirmationTokenId,RegistrationRequest registrationRequest){

        RegistirationEmailRequest registirationEmailRequest = RegistirationEmailRequest.builder()
                .to(registrationRequest.userName())
                .subject("Complete Registration!")
                .body(getwayUrl+USER_CONTROLLER+CONFIRM_ACCOUNT_API+"?token="+confirmationTokenId) //
                .build();
        return registirationEmailRequest;
    }

    @Async
    void sentEmail(RegistirationEmailRequest registirationEmailRequest){
        emailNotifClient.sendRegisterEmail(registirationEmailRequest);
    }

    public ResponseEntity<String> confirmUserAccount(String confirmationTokenId) {
        Optional<ConfirmationToken> confirmationToken = confirmationTokenRepository.findById(confirmationTokenId);

        if (confirmationToken.isPresent()) {
            Optional<User> user = userRepository.findById(confirmationToken.get().getUserId());
            user.get().setIsActive(true);
            userRepository.save(user.get());
            return ResponseEntity.ok("Email verified successfully!");
        }

        return ResponseEntity.badRequest().body("Error: Couldn't verify email");
    }

    public void testKafka(RegistrationRequest registrationRequest) {
        CustomerCreationRequest customerCreationRequest = authMapper.convertRegisRequestToCustomerRequest(registrationRequest);
        kafkaTemplate.send("customerProfileCreation",customerCreationRequest);
    }
}
