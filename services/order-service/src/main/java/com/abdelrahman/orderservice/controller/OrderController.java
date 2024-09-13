package com.abdelrahman.orderservice.controller;

import com.abdelrahman.orderservice.dto.kafka.OrderRequest;
import com.abdelrahman.orderservice.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.abdelrahman.orderservice.constant.Constant.OrderController.*;

@RestController
@RequestMapping(ORDER)
@AllArgsConstructor
@Slf4j
public class OrderController {
    private final OrderService orderService;

    @PostMapping(ADD_ORDER)
    public ResponseEntity<?> addOrder(@RequestBody OrderRequest orderRequest){
        return orderService.createOrder(orderRequest);
    }
}
