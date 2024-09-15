package com.example.emailnotifservice.controller;

import com.example.emailnotifservice.dto.CompletedOrderEmailRequest;
import com.example.emailnotifservice.dto.OrderFailedRequest;
import com.example.emailnotifservice.service.OrderStatusEmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.emailnotifservice.config.Constant.OrderStatusEmailController.*;

@RestController
@RequestMapping(ORDER_STATUS_EMAIL_API)
@RequiredArgsConstructor
@Slf4j
public class OrderStatusEmailController {

    private final OrderStatusEmailService emailService;

    @PostMapping(SEND_COMPLETED_ORDER_EMAIL)
    public ResponseEntity<Boolean> sendCompletedOrderEmail(@RequestBody CompletedOrderEmailRequest completedOrderEmailRequest){
        return emailService.sendCompletedOrderEmail(completedOrderEmailRequest);

    }

    @PostMapping(SEND_FAILED_ORDER_EMAIL)
    public ResponseEntity<Boolean> sendFailedOrderEmail(@RequestBody OrderFailedRequest orderFailedRequest){
        return emailService.sendFailedOrderEmail(orderFailedRequest);
    }
}
