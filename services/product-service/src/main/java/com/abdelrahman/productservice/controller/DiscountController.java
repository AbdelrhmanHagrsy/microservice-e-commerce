package com.abdelrahman.productservice.controller;

import com.abdelrahman.productservice.dto.DiscountDto;
import com.abdelrahman.productservice.dto.UserRoles;
import com.abdelrahman.productservice.repository.DiscountRepository;
import com.abdelrahman.productservice.service.DiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.abdelrahman.productservice.constant.Constant.DiscountController.*;


@RestController
@RequestMapping(PRODUCTS_DISCOUNT)
@RequiredArgsConstructor
public class DiscountController {

    private final DiscountRepository discountRepository;
    private final DiscountService discountService;

    @PostMapping(ADD_DISCOUNT)
    ResponseEntity<String> addDiscount(
            @RequestHeader(value = "X-User-Roles", required = true) String userRole,
            @RequestBody DiscountDto discountDto) {
        if (userRole.equals(UserRoles.SUPER_ADMIN.name()) || userRole.equals(UserRoles.PRODUCT_MANAGER.name())) {
            return discountService.addDiscount(discountDto);
        } else {
            return ResponseEntity.status(org.apache.http.HttpStatus.SC_UNAUTHORIZED).build();
        }
    }

    @PutMapping(UPDATE_DISCOUNT)
    ResponseEntity<String> updateDiscount(
            @RequestHeader(value = "X-User-Roles", required = true) String userRole,
            @PathVariable(name = "discount_id") Long id, @RequestBody DiscountDto discountDto) {
        if (userRole.equals(UserRoles.SUPER_ADMIN.name()) || userRole.equals(UserRoles.PRODUCT_MANAGER.name())) {
            return discountService.updateDiscount(id, discountDto);
        } else {
            return ResponseEntity.status(org.apache.http.HttpStatus.SC_UNAUTHORIZED).build();
        }
    }

    @GetMapping(GET_DISCOUNT)
    ResponseEntity<?> updateDiscount(@PathVariable(name = "discount_id") Long id){
        return discountService.getDiscount(id);
    }
}
