package com.abdelrhman.productnosqlservice.repository;

import com.abdelrhman.productnosqlservice.dto.ProductDto;
import com.abdelrhman.productnosqlservice.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface ProductCustomRepository {

    Page<Product> findProductsByFilter(Map<String,Object> filters, Pageable pageable);
}
