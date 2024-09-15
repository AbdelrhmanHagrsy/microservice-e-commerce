package com.abdelrahman.orderservice.client;

import com.abdelrahman.orderservice.dto.kafka.CompletedOrderEmailRequest;
import com.abdelrahman.orderservice.dto.kafka.OrderCreatedMessage;
import com.abdelrahman.orderservice.dto.kafka.OrderFailedRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import static com.abdelrahman.orderservice.constant.Constant.EmailNotifyClientConstant.*;

@FeignClient(name = EMAIL_SERVICE_NAME)
public interface EmailNotifyClient {
    @PostMapping(SEND_COMPLETED_ORDER_EMAIL)
    public ResponseEntity<Boolean> sendCompletedOrderEmail(@RequestBody CompletedOrderEmailRequest completedOrderEmailRequest);

    @PostMapping(SEND_FAILED_ORDER_EMAIL)
    public ResponseEntity<Boolean> sendFailedOrderEmail(@RequestBody OrderFailedRequest orderFailedRequest);
}
