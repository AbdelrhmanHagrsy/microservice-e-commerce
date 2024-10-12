package com.abdelrahman.orderservice.controller;

import com.abdelrahman.orderservice.dto.kafka.OrderRequest;
import com.abdelrahman.orderservice.dto.kafka.UserRoles;
import com.abdelrahman.orderservice.entity.Order;
import com.abdelrahman.orderservice.exception.InvalidOrderException;
import com.abdelrahman.orderservice.exception.OrderConflictExceotion;
import com.abdelrahman.orderservice.exception.OrderDeliveredFailed;
import com.abdelrahman.orderservice.exception.OrderPaidFailed;
import com.abdelrahman.orderservice.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<String> addOrder(@RequestBody OrderRequest orderRequest) {
        try {

            return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(orderRequest));

        } catch (InvalidOrderException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (OrderConflictExceotion ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        } catch (Exception ex) {
            log.error("Faild to create order: " + ex.getMessage());
            return ResponseEntity.internalServerError().body("Failed to create order!");
        }
    }

    @PutMapping(MARK_ORDER_AS_PAID_ORDER)
    public ResponseEntity<String> markOrderAsPaid(
            @RequestHeader(value = "X-User-Roles", required = true) String userRole,
            @PathVariable(name = "order_id") String orderId) {
        try {

            // check user role before add a new product
            if (userRole.equals(UserRoles.SUPER_ADMIN.name()) || userRole.equals(UserRoles.ORDER_MANAGER.name())) {
                return ResponseEntity.ok().body(orderService.markOrderAsPaid(orderId));
            } else {
                return ResponseEntity.status(org.apache.http.HttpStatus.SC_UNAUTHORIZED).body("You do not have permission to update product.");
            }

        } catch (OrderPaidFailed e){
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body(ex.getMessage());
        }
    }
    @PutMapping(MARK_ORDER_AS_DELIVERED_ORDER)
    public ResponseEntity<String> markOrderAsDelivered(
            @RequestHeader(value = "X-User-Roles", required = true) String userRole,
            @PathVariable(name = "order_id") String orderId) {
        try {

            // check user role before add a new product
            if (userRole.equals(UserRoles.SUPER_ADMIN.name()) || userRole.equals(UserRoles.ORDER_MANAGER.name())) {
                return ResponseEntity.ok().body(orderService.markOrderAsDelivered(orderId));
            } else {
                return ResponseEntity.status(org.apache.http.HttpStatus.SC_UNAUTHORIZED).body("You do not have permission to update product.");
            }

        } catch (OrderDeliveredFailed e){
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body(ex.getMessage());
        }
    }

    @GetMapping(FETCH_ORDERS_BY_USER_NAME)
    public ResponseEntity<List<Order>> fetchAllOrdersByUserEmail(@PathVariable(required = true, name = "userName") String userName) {
        try {
            return ResponseEntity.ok().body(orderService.fetchAllOrdersByUserEmail(userName));
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(FETCH_CANCELED_ORDERS_BY_USER_NAME)
    public ResponseEntity<List<Order>> fetchAllCanceledOrdersByUserEmail(@PathVariable(required = true, name = "userName") String userName) {
        try {
            return ResponseEntity.ok().body(orderService.fetchAllCanceledOrdersByUserEmail(userName));
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(FETCH_COMPLETED_ORDERS_BY_USER_NAME)
    public ResponseEntity<List<Order>> fetchAllCompletedOrdersByUserEmail(@PathVariable(required = true, name = "userName") String userName) {
        try {
            return ResponseEntity.ok().body(orderService.fetchAllCompletedOrdersByUserEmail(userName));
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
