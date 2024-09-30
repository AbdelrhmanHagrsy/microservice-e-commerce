package com.abdelrhman.productnosqlservice.repository;

import com.abdelrhman.productnosqlservice.dto.ProductDto;
import com.abdelrhman.productnosqlservice.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductCustomRepositoryImpl implements ProductCustomRepository{


    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Page<Product> findProductsByFilter(Map<String, Object> filters, Pageable pageable) {
        Query query = new Query();
        List<Criteria> criteriaList = new ArrayList<>();


        if(filters.containsKey("productName")){
            criteriaList.add(Criteria.where("productName").regex((String) filters.get("productName"), "i")); // case-insensitive
        }

        if(filters.containsKey("categoryName")){
            criteriaList.add(Criteria.where("categoryName").is(filters.get("categoryName")));
        }

        if(filters.containsKey("minPrice") && filters.containsKey("maxPrice")){
            criteriaList.add(Criteria.where("price").gte(filters.get("minPrice")).lte(filters.get("maxPrice")));
        }

        if(!criteriaList.isEmpty()){
            query.addCriteria(new Criteria().andOperator(criteriaList.toArray(new Criteria[0])));
        }

        // Apply pagination
        query.with(pageable);

        List<Product> productList = mongoTemplate.find(query,Product.class);
        //
        Long total = mongoTemplate.count(query,Product.class);

        return new PageImpl<>(productList,pageable,total);
    }
}
