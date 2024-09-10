package com.abdelrhman.inventoryservice.controller;

import com.abdelrhman.inventoryservice.dto.CartItemDto;
import com.abdelrhman.inventoryservice.entity.CartItem;
import com.abdelrhman.inventoryservice.service.CartItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.abdelrhman.inventoryservice.constant.Constant.CartItemController.*;

@RestController
@RequestMapping(CART_ITEM)
@RequiredArgsConstructor
@Slf4j
public class CartItemController {
    private final CartItemService cartItemService;

    @PostMapping(ADD_CART_ITEM)
    public ResponseEntity<?> addCartItem(@RequestBody CartItemDto cartItemDto){
        return cartItemService.addCartItem(cartItemDto);
    }

    @PutMapping(UPDATE_CART_ITEM)
    public ResponseEntity<?> updateCartItem(@PathVariable(name = "cart_id") String id,@RequestBody CartItemDto cartItemDto){
        return cartItemService.updateCartItem(id,cartItemDto);
    }

    @DeleteMapping(REMOVE_CART_ITEM)
    public ResponseEntity<String> removeCartItem(@PathVariable(name = "cart_id") String id){
        return cartItemService.removeCartItem(id);
    }

    @GetMapping(GET_ALL_CART_ITEM)
    public ResponseEntity<?> getAllShoppingSessionItems(@PathVariable(name = "session_id")String sessionId){
        return cartItemService.getAllCartItemsForShoppingSession(sessionId);
    }
}
