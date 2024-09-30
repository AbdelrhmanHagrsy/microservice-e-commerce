package com.abdelrahman.orderservice.controller;

import com.abdelrahman.orderservice.dto.kafka.OrderRequest;
import com.abdelrahman.orderservice.entity.Order;
import com.abdelrahman.orderservice.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping(FETCH_ORDERS_BY_USER_NAME)
    public ResponseEntity<List<Order>> fetchOrdersByUserEmail(@PathVariable(required = true, name = "userName") String userName) {
        try {
            return ResponseEntity.ok().body(orderService.fetchUserOrders(userName));
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
