package com.abdelrhman.inventoryservice.repository;

import com.abdelrhman.inventoryservice.entity.CartItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends MongoRepository<CartItem,String> {

    List<CartItem> findBySessionId(String sessionId);
}
