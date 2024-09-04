package com.abdelrahman.productservice.mapper;

import com.abdelrahman.productservice.dto.ProductInventoryDto;
import com.abdelrahman.productservice.entity.ProductInventory;
import org.springframework.stereotype.Component;

@Component
public class ProductInventoryMapper {

    public ProductInventory convertDtoToEntity(ProductInventoryDto productInventoryDto){
        return ProductInventory.builder()
                .id(productInventoryDto.getId())
                .quantity(productInventoryDto.getQuintity())
                .location(productInventoryDto.getLocation())
                .inventoryStatus(productInventoryDto.getInventoryStatus())
                .build();
    }

    public ProductInventoryDto convertEntityToDto(ProductInventory productInventory){
        return ProductInventoryDto.builder()
                .id(productInventory.getId())
                .location(productInventory.getLocation())
                .quintity(productInventory.getQuantity())
                .build();
    }
}
