package com.abdelrahman.productservice.controller;

import com.abdelrahman.productservice.dto.request.ProductRequest;
import com.abdelrahman.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.abdelrahman.productservice.constant.Constant.ProductController.*;


@RestController
@RequestMapping(PRODUCTS_PRODUCT)
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping(ADD_PRODUCT)
    public ResponseEntity<String> addProduct(@RequestBody ProductRequest productRequest){
        return productService.addProduct(productRequest);
    }

    @PutMapping(UPDATE_PRODUCT)
    public ResponseEntity<String> updateProduct(@PathVariable(name = "product_id") Long id,@RequestBody ProductRequest productRequest){
        return productService.updateProduct(id,productRequest);
    }



}
