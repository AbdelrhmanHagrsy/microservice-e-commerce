package com.abdelrhman.inventoryservice.controller;

import com.abdelrhman.inventoryservice.entity.ShoppingSession;
import com.abdelrhman.inventoryservice.service.ShoppingSessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.abdelrhman.inventoryservice.constant.Constant.ShoppingSessionController.*;

@RestController
@RequestMapping(SHOPPING_SESSION)
@RequiredArgsConstructor
@Slf4j
public class ShoppingSessionController {

    private final ShoppingSessionService shoppingSessionService;

    @PostMapping(ADD_SHOPPING_SESSION)
    public ResponseEntity<ShoppingSession> addShoppingSession(@PathVariable(name = "customer_id") Long customerId,@PathVariable(name = "customer_user_name") String customerUserName){
        return shoppingSessionService.addShoppingSession(customerId,customerUserName);
    }

    @PutMapping(GET_SHOPPING_SESSION)
    public ResponseEntity<ShoppingSession> getShoppingSession(@PathVariable(name = "session_id") String sessionId){
        return shoppingSessionService.getShoppingSession(sessionId);
    }

}
