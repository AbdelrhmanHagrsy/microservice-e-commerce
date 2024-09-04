package com.abdelrahman.productservice.service;

import com.abdelrahman.productservice.dto.ProductCategoryDto;
import com.abdelrahman.productservice.entity.ProductCategory;
import com.abdelrahman.productservice.exception.EntityNotFound;
import com.abdelrahman.productservice.mapper.ProductCategoryMapper;
import com.abdelrahman.productservice.repository.ProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProdcutCategoryService {

    private final ProductCategoryRepository productCategoryRepository;
    private final ProductCategoryMapper productCategoryMapper;

    public ResponseEntity<String> addProductCategory(ProductCategoryDto productCategoryDto) {
        try {
            ProductCategory productCategory = productCategoryMapper.convertDtoToEntity(productCategoryDto);
            productCategoryRepository.save(productCategory);
            return ResponseEntity.ok("Product category added successfully.");

        } catch (Exception exception) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred while adding the product category.");
        }
    }

    public ResponseEntity<String> updateProductCategory(Long categoryId, ProductCategoryDto productCategoryDto) {
        try {

            ProductCategory productCategory = productCategoryRepository.findById(categoryId)
                    .orElseThrow(() -> new EntityNotFound(String.format("Product category with ID %s not found.", categoryId)));
            //
            productCategory.setName(productCategoryDto.getCategoryName());
            productCategory.setDesc(productCategoryDto.getDescription());
            productCategoryRepository.save(productCategory);
            return ResponseEntity.ok("Product category updated successfully.");

        } catch (EntityNotFound exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        } catch (Exception exception) {
            log.error("Error while updating product category", exception.getMessage());
            return ResponseEntity.internalServerError().body("An unexpected error occurred while updating the product category.");
        }
    }

    public ResponseEntity<?> getProductCategory(Long categoryId) {
        try {

            ProductCategory productCategory = productCategoryRepository.findById(categoryId)
                    .orElseThrow(() -> new EntityNotFound(String.format("Product category with ID %s not found.", categoryId)));
            //
            ProductCategoryDto productCategoryDto= productCategoryMapper.convertEntityToDto(productCategory);
            return  ResponseEntity.ok().body(productCategoryDto);

        } catch (EntityNotFound exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }


}
