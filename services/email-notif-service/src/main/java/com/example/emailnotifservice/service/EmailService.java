package com.example.emailnotifservice.service;

import com.example.emailnotifservice.dto.RegistirationEmailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public Boolean sendRegistirationEmail(RegistirationEmailRequest registirationEmailRequest){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(registirationEmailRequest.to());
        simpleMailMessage.setSubject(registirationEmailRequest.subject());
        simpleMailMessage.setText(registirationEmailRequest.body());
        javaMailSender.send(simpleMailMessage);
        return true;
    }
}
