package com.abdelrahman.productservice.mapper;

import com.abdelrahman.productservice.dto.ProductCategoryDto;
import com.abdelrahman.productservice.entity.ProductCategory;
import org.springframework.stereotype.Component;

@Component
public class ProductCategoryMapper {


    public ProductCategory convertDtoToEntity(ProductCategoryDto productCategoryDto){
        return ProductCategory.builder()
                .name(productCategoryDto.getCategoryName())
                .desc(productCategoryDto.getDescription())
                .build();
    }

    public ProductCategoryDto convertEntityToDto(ProductCategory productCategory){
        return ProductCategoryDto.builder()
                .id(productCategory.getId())
                .categoryName(productCategory.getName())
                .description(productCategory.getDesc())
                .build();
    }
}
