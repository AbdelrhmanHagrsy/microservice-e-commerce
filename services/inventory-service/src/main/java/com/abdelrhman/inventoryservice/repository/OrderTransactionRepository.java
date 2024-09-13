package com.abdelrhman.inventoryservice.repository;

import com.abdelrhman.inventoryservice.entity.OrderTransaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface OrderTransactionRepository extends MongoRepository<OrderTransaction, UUID> {
}
