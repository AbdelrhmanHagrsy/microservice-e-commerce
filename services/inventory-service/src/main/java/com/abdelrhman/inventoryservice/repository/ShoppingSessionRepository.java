package com.abdelrhman.inventoryservice.repository;

import com.abdelrhman.inventoryservice.entity.ShoppingSession;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingSessionRepository extends MongoRepository<ShoppingSession,String> {
}
