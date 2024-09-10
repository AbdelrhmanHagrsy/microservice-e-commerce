package com.abdelrhman.inventoryservice.service;

import com.abdelrhman.inventoryservice.dto.CartItemDto;
import com.abdelrhman.inventoryservice.entity.CartItem;
import com.abdelrhman.inventoryservice.entity.ProductInventory;
import com.abdelrhman.inventoryservice.entity.ShoppingSession;
import com.abdelrhman.inventoryservice.exception.EntityNotFound;
import com.abdelrhman.inventoryservice.exception.QuantityNotAvailable;
import com.abdelrhman.inventoryservice.repository.CartItemRepository;
import com.abdelrhman.inventoryservice.repository.ProductInventoryRepository;
import com.abdelrhman.inventoryservice.repository.ShoppingSessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartItemService {
    private final ShoppingSessionRepository shoppingSessionRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductInventoryRepository productInventoryRepository;

    public ResponseEntity<?> addCartItem(CartItemDto cartItemDto) {
        try {
            shoppingSessionRepository.findById(cartItemDto.getSessionId())
                    .orElseThrow(() -> new EntityNotFound(String.format("Shopping session with id : %s not found!", cartItemDto.getSessionId())));
            ProductInventory productInventory = productInventoryRepository.findById(cartItemDto.getProductId())
                    .orElseThrow(() -> new EntityNotFound(String.format("Product with id : %s not found!", cartItemDto.getProductId())));
            if(cartItemDto.getQuantity() > productInventory.getAvailableQuantity())
                throw new QuantityNotAvailable(String.format("Available quantity for this product is: %s please reduce quantity amount",productInventory.getAvailableQuantity()));

            CartItem cartItem = cartItemRepository.save(
                    CartItem.builder()
                            .sessionId(cartItemDto.getSessionId())
                            .productId(cartItemDto.getProductId())
                            .quantity(cartItemDto.getQuantity())
                            .createdAt(LocalDate.now())
                            .build());
            //
            return ResponseEntity.status(HttpStatus.CREATED).body(cartItem);
        } catch (EntityNotFound | QuantityNotAvailable exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    public ResponseEntity<?> updateCartItem(String id,CartItemDto cartItemDto) {
        try {
            shoppingSessionRepository.findById(cartItemDto.getSessionId())
                    .orElseThrow(() -> new EntityNotFound(String.format("Shopping session with id : %s not found!", cartItemDto.getSessionId())));
            //
            CartItem cartItem = cartItemRepository.findById(id)
                    .orElseThrow(()->new EntityNotFound(String.format("Cart item with id : %s not found!", id)));

            ProductInventory productInventory = productInventoryRepository.findById(cartItemDto.getProductId())
                    .orElseThrow(() -> new EntityNotFound(String.format("Product with id : %s not found!", cartItemDto.getProductId())));
            if(cartItemDto.getQuantity() > productInventory.getAvailableQuantity())
                throw new QuantityNotAvailable(String.format("Available quantity for this product is: %s please reduce quantity amount",productInventory.getAvailableQuantity()));

            // update cart item quantity only
            cartItem.setQuantity(cartItemDto.getQuantity());
            cartItem.setModifiedAt(LocalDate.now());
            //
            return ResponseEntity.ok().body(cartItemRepository.save(cartItem));
        }catch (EntityNotFound | QuantityNotAvailable exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    public ResponseEntity<String> removeCartItem(String id) {
        try {
            CartItem cartItem = cartItemRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFound(String.format("Cart item with id : %s not found!", id)));
            cartItemRepository.deleteById(cartItem.getId());
            //
            return ResponseEntity.ok().body("Cart Item deleted successfully");
        } catch (EntityNotFound entityNotFound) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    public ResponseEntity<?> getAllCartItemsForShoppingSession(String sessionId){
        try {
           ShoppingSession shoppingSession = shoppingSessionRepository.findById(sessionId)
                    .orElseThrow(() -> new EntityNotFound(String.format("Shopping session with id : %s not found!", sessionId)));
            List<CartItem> cartItems = cartItemRepository.findBySessionId(sessionId);
            //
            return ResponseEntity.status(HttpStatus.CREATED).body(cartItems);
        }catch (EntityNotFound entityNotFound){
            return ResponseEntity.badRequest().body(entityNotFound.getMessage());
        }
    }
}
