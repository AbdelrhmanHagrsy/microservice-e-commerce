package com.abdelrahman.productservice.mapper;

import com.abdelrahman.productservice.dto.*;
import com.abdelrahman.productservice.dto.kafka.ProductMessage;
import com.abdelrahman.productservice.dto.request.ProductRequest;
import com.abdelrahman.productservice.entity.Product;
import com.abdelrahman.productservice.entity.ProductInventory;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {


    public Product updateProductInformation(Product product, ProductRequest productRequest) {
        product.setName(productRequest.getProductName());
        product.setDesc(productRequest.getDescription());
        product.setSku(productRequest.getSku());
        product.setPrice(productRequest.getPrice());
        return product;
    }
    public Product convertProductDtoToEntity(ProductRequest productDto) {
        return Product.builder()
                .name(productDto.getProductName())
                .desc(productDto.getDescription())
                .sku(productDto.getSku())
                .price(productDto.getPrice())
                .build();
    }

    public ProductInventory convertProductInventoryDtoToEntity(ProductInventoryDto productInventoryDto){
        return ProductInventory.builder()
                .quantity(productInventoryDto.getQuintity())
                .location(productInventoryDto.getLocation())
                .inventoryStatus(productInventoryDto.getInventoryStatus())
                .build();
    }

    public ProductInventory getDefaultInventory(){
        return ProductInventory.builder()
                .quantity(0)
                .inventoryStatus(InventoryStatus.OUT_OF_STOCK)
                .build();
    }

    public ProductMessage prepareProductMessage(Product product,ProductMessageStatus productMessageStatus) {

        ProductMessage productMessage = new ProductMessage();
        productMessage.setProductId(product.getId());
        productMessage.setProductName(product.getName());
        productMessage.setCategoryName(product.getProductCategory().getName());
        productMessage.setDesc(product.getDesc());
        productMessage.setSku(product.getSku());
        productMessage.setPrice(product.getPrice());
        productMessage.setQuantity(product.getProductInventory().getQuantity());
        if(product.getDiscount() != null){
            productMessage.setDiscountName(product.getDiscount().getName());
            productMessage.setDiscountPercent(product.getDiscount().getDiscountPercent());
            productMessage.setDiscountActive(product.getDiscount().getActive());
            productMessage.setDiscountId(product.getDiscount().getId());
        }
        productMessage.setProductInventoryId(product.getProductInventory().getId());
        productMessage.setProductCategoryId(product.getProductCategory().getId());
        productMessage.setProductMessageStatus(productMessageStatus);
        productMessage.setLastModifiedDate(product.getLastModifiedDate());
        return productMessage;
    }
}
