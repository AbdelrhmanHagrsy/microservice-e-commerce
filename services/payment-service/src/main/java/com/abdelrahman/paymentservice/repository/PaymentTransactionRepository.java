package com.abdelrahman.paymentservice.repository;

import com.abdelrahman.paymentservice.entity.PaymentTransaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface PaymentTransactionRepository extends MongoRepository<PaymentTransaction, UUID> {
}