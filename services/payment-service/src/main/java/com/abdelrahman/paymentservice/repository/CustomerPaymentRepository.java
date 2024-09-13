package com.abdelrahman.paymentservice.repository;

import com.abdelrahman.paymentservice.entity.CustomerPayment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerPaymentRepository extends MongoRepository<CustomerPayment,String> {
    Optional<CustomerPayment> findByIdAndCustomerId(String id,String customerId);
}
