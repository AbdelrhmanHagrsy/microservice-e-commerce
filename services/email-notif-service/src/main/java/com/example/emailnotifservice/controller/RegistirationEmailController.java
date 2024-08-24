package com.example.emailnotifservice.controller;

import com.example.emailnotifservice.dto.RegistirationEmailRequest;
import com.example.emailnotifservice.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.emailnotifservice.config.Constant.ApisMapping.REGISTER_NEW_USER;
import static com.example.emailnotifservice.config.Constant.ControllersMapping.AUTH;

@RestController
@RequestMapping(AUTH)
@RequiredArgsConstructor
@Slf4j
public class RegistirationEmailController {
    private final EmailService emailService;


    @PostMapping(REGISTER_NEW_USER)
    public ResponseEntity<Boolean> sendRegisterEmail(@RequestBody RegistirationEmailRequest registirationEmailRequest){
        try {
            return ResponseEntity.ok().body(emailService.sendRegistirationEmail(registirationEmailRequest));
        }catch (Exception exception){
            log.error(exception.getMessage());
            return ResponseEntity.badRequest().body(false);
        }
    }

}
