package com.abdelrahman.productservice.controller;

import com.abdelrahman.productservice.dto.DiscountDto;
import com.abdelrahman.productservice.dto.ProductInventoryDto;
import com.abdelrahman.productservice.repository.ProductInventoryRepository;
import com.abdelrahman.productservice.service.ProductInventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.abdelrahman.productservice.constant.Constant.DiscountController.*;
import static com.abdelrahman.productservice.constant.Constant.ProductInventoryController.*;

@RestController
@RequestMapping(PRODUCTS_INVENTORY)
@RequiredArgsConstructor
public class ProductInventoryController {
    private final ProductInventoryRepository productInventoryRepository;
    private final ProductInventoryService productInventoryService;

//    @PostMapping(ADD_INVENTORY)
//    ResponseEntity<String> addDiscount(@RequestBody ProductInventoryDto productInventoryDto){
//        return productInventoryService.addInventory(productInventoryDto);
//    }
//
//    @PutMapping(UPDATE_INVENTORY)
//    ResponseEntity<String> updateDiscount(@PathVariable(name = "discount_id") Long id,@RequestBody ProductInventoryDto productInventoryDto){
//        return productInventoryService.updateInventory(id,productInventoryDto);
//    }
//
//    @GetMapping(GET_INVENTORY)
//    ResponseEntity<?> updateDiscount(@PathVariable(name = "discount_id") Long id){
//        return productInventoryService.getInventory(id);
//    }

}
