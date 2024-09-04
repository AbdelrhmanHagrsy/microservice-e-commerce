package com.abdelrahman.productservice.service;

import com.abdelrahman.productservice.dto.ProductInventoryDto;
import com.abdelrahman.productservice.entity.Discount;
import com.abdelrahman.productservice.entity.ProductInventory;
import com.abdelrahman.productservice.exception.EntityNotFound;
import com.abdelrahman.productservice.mapper.ProductInventoryMapper;
import com.abdelrahman.productservice.repository.ProductInventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductInventoryService {

    private final ProductInventoryRepository productInventoryRepository;
    private final ProductInventoryMapper productInventoryMapper;

//    public ResponseEntity<String> addInventory(ProductInventoryDto productInventoryDto) {
//        try {
//            ProductInventory productInventory = productInventoryMapper.convertDtoToEntity(productInventoryDto);
//            productInventoryRepository.save(productInventory);
//            return ResponseEntity.ok("Product Inventory added successfully.");
//
//        } catch (Exception exception) {
//            log.error("Error while adding Discount", exception.getMessage());
//            return ResponseEntity.internalServerError().body("An unexpected error occurred while adding the product Inventory.");
//        }
//    }
//
//    public ResponseEntity<String> updateInventory(Long id, ProductInventoryDto productInventoryDto) {
//
//    }
//
//    public ResponseEntity<?> getInventory(Long id) {
//    }
}
