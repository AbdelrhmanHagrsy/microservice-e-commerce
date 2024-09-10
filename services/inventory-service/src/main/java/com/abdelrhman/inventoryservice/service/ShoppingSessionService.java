package com.abdelrhman.inventoryservice.service;

import com.abdelrhman.inventoryservice.entity.ShoppingSession;
import com.abdelrhman.inventoryservice.exception.EntityNotFound;
import com.abdelrhman.inventoryservice.repository.ShoppingSessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShoppingSessionService {
    private final ShoppingSessionRepository shoppingSessionRepository;

    public ResponseEntity<ShoppingSession> addShoppingSession(Long customerId, String customerUserName) {
        try {
            ShoppingSession shoppingSession = shoppingSessionRepository.save(
                    ShoppingSession.builder()
                            .customerId(customerId)
                            .customerUserName(customerUserName)
                            .createdAt(LocalDate.now())
                            .build());
            return ResponseEntity.status(HttpStatus.CREATED).body(shoppingSession);
        }catch(Exception exception){
            return  ResponseEntity.internalServerError().body(null);
        }

    }

    public ResponseEntity<ShoppingSession> getShoppingSession(String sessionId) {
        try {
            ShoppingSession shoppingSession = shoppingSessionRepository.findById(sessionId)
                    .orElseThrow(()-> new EntityNotFound(String.format("Shopping Session with id: %s is not found!",sessionId)));
            return ResponseEntity.ok().body(shoppingSession);
        }catch (EntityNotFound entityNotFound){
            return ResponseEntity.badRequest().body(null);
        }
    }


}
