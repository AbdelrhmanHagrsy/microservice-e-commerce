package com.abdelrhman.inventoryservice.repository;

import com.abdelrhman.inventoryservice.entity.ProductInventory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  ProductInventoryRepository extends MongoRepository<ProductInventory,String> {
}
