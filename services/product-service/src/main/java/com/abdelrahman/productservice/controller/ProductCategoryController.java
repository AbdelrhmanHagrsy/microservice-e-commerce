package com.abdelrahman.productservice.controller;

import com.abdelrahman.productservice.dto.ProductCategoryDto;
import com.abdelrahman.productservice.service.ProdcutCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.abdelrahman.productservice.constant.Constant.ProductCategoryController.*;


@RestController
@RequestMapping(PRODUCTS_CATEGORY)
@RequiredArgsConstructor
public class ProductCategoryController {
    private final ProdcutCategoryService prodcutCategoryService;


    @PostMapping(ADD_PRODUCT_CATEGORY)
    ResponseEntity<String> addProductategory(@RequestBody ProductCategoryDto productCategoryDto){
        return  prodcutCategoryService.addProductCategory(productCategoryDto);
    }

    @PutMapping(UPDATE_PRODUCT_CATEGORY)
    ResponseEntity<String> addProductategory(@PathVariable(name = "category_id") Long id,@RequestBody ProductCategoryDto productCategoryDto){
        return  prodcutCategoryService.updateProductCategory(id,productCategoryDto);
    }

    @GetMapping(GET_PRODUCT_CATEGORY)
    ResponseEntity<?> addProductategory(@PathVariable(name = "category_id") Long id){
        return  prodcutCategoryService.getProductCategory(id);
    }
}
