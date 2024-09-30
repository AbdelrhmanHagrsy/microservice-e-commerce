package com.abdelrhman.productnosqlservice.controller;

import com.abdelrhman.productnosqlservice.service.ProductService;
import com.abdelrhman.productnosqlservice.dto.ProductFilterRequest;
import com.abdelrhman.productnosqlservice.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.abdelrhman.productnosqlservice.constant.Constant.ProductController.*;

@RestController
@RequestMapping(PRODUCT_PATH)
@RequiredArgsConstructor
public class productController {

    private final ProductService productService;


    @GetMapping(GET_ALL_PRODUCT_WITH_FILTERS)
    public ResponseEntity<Page<Product>> fetchAllProductsWithFilters(@RequestBody ProductFilterRequest productFilterRequest){
        try{
            return  ResponseEntity.ok().body(productService.fetchAllProductsWithFilters(productFilterRequest));
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

}
