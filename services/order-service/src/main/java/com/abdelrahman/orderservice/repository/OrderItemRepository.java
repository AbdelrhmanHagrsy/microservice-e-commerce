package com.abdelrahman.orderservice.repository;

import com.abdelrahman.orderservice.entity.OrderItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends MongoRepository<OrderItem,String> {
}
