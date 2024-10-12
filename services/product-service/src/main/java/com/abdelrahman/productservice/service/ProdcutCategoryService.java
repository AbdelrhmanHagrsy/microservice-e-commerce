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

    public String addProductCategory(ProductCategoryDto productCategoryDto) {
        try {
            ProductCategory productCategory = productCategoryMapper.convertDtoToEntity(productCategoryDto);
            productCategoryRepository.save(productCategory);
            return "Product category added successfully.";

        } catch (Exception exception) {
            throw new RuntimeException("An unexpected error occurred while adding the product category.");
        }
    }

    public String updateProductCategory(Long categoryId, ProductCategoryDto productCategoryDto) {
        try {

            ProductCategory productCategory = productCategoryRepository.findById(categoryId)
                    .orElseThrow(() -> new EntityNotFound(String.format("Product category with ID %s not found.", categoryId)));
            //
            productCategory.setName(productCategoryDto.getCategoryName());
            productCategory.setDesc(productCategoryDto.getDescription());
            productCategoryRepository.save(productCategory);
            return "Product category updated successfully.";

        } catch (EntityNotFound exception) {
            throw exception;
        } catch (Exception exception) {
            log.error("Error while updating product category", exception.getMessage());
            throw new RuntimeException("An unexpected error occurred while updating the product category.");
        }
    }

    public ProductCategoryDto getProductCategory(Long categoryId) {
        try {

            ProductCategory productCategory = productCategoryRepository.findById(categoryId)
                    .orElseThrow(() -> new EntityNotFound(String.format("Product category with ID %s not found.", categoryId)));
            //
            ProductCategoryDto productCategoryDto = productCategoryMapper.convertEntityToDto(productCategory);
            return productCategoryDto;

        } catch (EntityNotFound exception) {
            throw exception;
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while fetching the product category.");
        }
    }


}
