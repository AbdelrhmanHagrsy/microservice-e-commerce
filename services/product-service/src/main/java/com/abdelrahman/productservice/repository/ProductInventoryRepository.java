package com.abdelrahman.productservice.repository;

import com.abdelrahman.productservice.entity.ProductInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductInventoryRepository extends JpaRepository<ProductInventory,Integer> {
}
