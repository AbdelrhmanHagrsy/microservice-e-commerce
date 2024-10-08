package com.abdelrhman.productnosqlservice.repository;

import com.abdelrhman.productnosqlservice.entity.Product;
import org.springframework.data.mongodb.SpringDataMongoDB;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<Product,String> , ProductCustomRepository{

    Optional<Product> findByProductId(Long ProductId);
}
