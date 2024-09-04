package com.abdelrahman.productservice.controller;

import com.abdelrahman.productservice.dto.DiscountDto;
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
    ResponseEntity<String> addDiscount(@RequestBody DiscountDto discountDto){
        return discountService.addDiscount(discountDto);
    }

    @PutMapping(UPDATE_DISCOUNT)
    ResponseEntity<String> updateDiscount(@PathVariable(name = "discount_id") Long id,@RequestBody DiscountDto discountDto){
        return discountService.updateDiscount(id,discountDto);
    }

    @GetMapping(GET_DISCOUNT)
    ResponseEntity<?> updateDiscount(@PathVariable(name = "discount_id") Long id){
        return discountService.getDiscount(id);
    }
}
