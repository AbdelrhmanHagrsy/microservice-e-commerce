package com.abdelrahman.productservice.repository;

import com.abdelrahman.productservice.entity.Offer;
import com.abdelrahman.productservice.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer,Long> {

    List<Offer> findAllByIsActiveAndProductCategory_Id(Boolean isActive,Long productCategory);
    List<Offer> findAllByIsActive(Boolean isActive);

}
