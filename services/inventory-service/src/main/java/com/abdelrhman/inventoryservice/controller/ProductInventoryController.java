package com.abdelrhman.inventoryservice.controller;


import com.abdelrhman.inventoryservice.dto.CancelOrderInventoryDeductionRequest;
import com.abdelrhman.inventoryservice.dto.ProductInventoryDto;
import com.abdelrhman.inventoryservice.service.ProductInventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.abdelrhman.inventoryservice.constant.Constant.ProductInventoryController.*;

@RestController
@RequestMapping(PRODUCT_INVENTORY)
@RequiredArgsConstructor
@Slf4j
public class ProductInventoryController {

    private final ProductInventoryService productInventoryService;


    @PostMapping(ADD_PRODUCT_INVENTORY)
    public ResponseEntity<?> addProductInventory(@Valid @RequestBody ProductInventoryDto productInventoryDto){
        return productInventoryService.addProductInventory(productInventoryDto);
    }

    @PutMapping(UPDATE_PRODUCT_INVENTORY)
    public ResponseEntity<?> updateProductInventory(@Valid @PathVariable(name = "inventory_id") String inventoryId,@Valid  @RequestBody ProductInventoryDto productInventoryDto){
        return productInventoryService.updateProductInventory(inventoryId,productInventoryDto);
    }

    @GetMapping(GET_PRODUCT_INVENTORY)
    public ResponseEntity<?> getProductInventory(@PathVariable(name = "inventory_id") String inventoryId){
        return productInventoryService.getProductInventory(inventoryId);
    }

    @PutMapping(CANCEL_ORDER_PRODUCT_INVENTORY_DEDUCTION)
    public ResponseEntity<Boolean> cancelOrderProductInventoryDeduction(@RequestBody CancelOrderInventoryDeductionRequest cancelOrderInventoryDeductionRequest){
        return productInventoryService.cancelOrderDeduction(cancelOrderInventoryDeductionRequest);
    }
}
