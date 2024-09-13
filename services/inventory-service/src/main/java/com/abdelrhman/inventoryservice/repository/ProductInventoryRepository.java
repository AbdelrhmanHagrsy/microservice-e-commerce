package com.abdelrhman.inventoryservice.repository;

import com.abdelrhman.inventoryservice.entity.ProductInventory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface  ProductInventoryRepository extends MongoRepository<ProductInventory,String> {
    Optional<ProductInventory> findByProductId(String productId);
}
