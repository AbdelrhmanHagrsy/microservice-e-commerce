package com.abdelrahaman.authenticationservice.client;

import com.abdelrahaman.authenticationservice.dto.RegistirationEmailRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.abdelrahaman.authenticationservice.constant.Constant.Client.GET_USER_BY_USER_NAME;
import static com.abdelrahaman.authenticationservice.constant.Constant.EmailNotif.EMAIL_NOTIFY_SERVICE;
import static com.abdelrahaman.authenticationservice.constant.Constant.EmailNotif.SEND_REGISTERATION_EMAIL;

@FeignClient(name = EMAIL_NOTIFY_SERVICE)
public interface EmailNotifClient {

    @PostMapping(SEND_REGISTERATION_EMAIL)
    public ResponseEntity<Boolean> sendRegisterEmail(@RequestBody RegistirationEmailRequest registirationEmailRequest);
}
