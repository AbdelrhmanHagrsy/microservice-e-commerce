package com.abdelrahaman.authenticationservice.repository;

import com.abdelrahaman.authenticationservice.entity.ConfirmationToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmationTokenRepository extends MongoRepository<ConfirmationToken,String> {
}
