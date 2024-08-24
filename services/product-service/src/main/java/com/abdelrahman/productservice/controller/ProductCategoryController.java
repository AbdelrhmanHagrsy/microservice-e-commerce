package com.abdelrahman.productservice.controller;

import com.abdelrahman.productservice.service.ProdcutCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.abdelrahman.productservice.constant.Constant.ApisMapping.PRODUCT_CATEGORY_GET_ALL;
import static com.abdelrahman.productservice.constant.Constant.ControllersMapping.PRODUCT_CATEGORY;

@RestController
@RequestMapping(PRODUCT_CATEGORY)
@RequiredArgsConstructor
public class ProductCategoryController {
    private final ProdcutCategoryService prodcutCategoryService;

    @GetMapping(PRODUCT_CATEGORY_GET_ALL)
    public ResponseEntity<?> getAllCategory(){

        return ResponseEntity.ok("getway working well");
    }
}
