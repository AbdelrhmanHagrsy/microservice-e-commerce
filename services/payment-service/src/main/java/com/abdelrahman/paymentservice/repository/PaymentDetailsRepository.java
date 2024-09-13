package com.abdelrahman.paymentservice.repository;

import com.abdelrahman.paymentservice.entity.PaymentDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentDetailsRepository extends MongoRepository<PaymentDetails,String> {

}
