package com.abdelrhman.productnosqlservice;

import com.abdelrhman.productnosqlservice.entity.Product;
import com.abdelrhman.productnosqlservice.dto.ProductMessage;
import com.abdelrhman.productnosqlservice.dto.ProductMessageStatus;
import com.abdelrhman.productnosqlservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public Boolean addProduct(ProductMessage productMessage){

        Product product = productMessage.getProductMessageStatus().equals(ProductMessageStatus.CREATE) ? new Product()
                : productRepository.findByProductId(productMessage.getProductId()).get();
        product.setProductName(productMessage.getProductName());
        product.setCategoryName(productMessage.getCategoryName());
        product.setDesc(productMessage.getDesc());
        product.setSku(productMessage.getSku());
        product.setPrice(productMessage.getPrice());
        product.setQuantity(productMessage.getQuantity());
        product.setDiscountName(productMessage.getDiscountName());
        product.setDiscountPercent(productMessage.getDiscountPercent());
        product.setDiscountActive(productMessage.getDiscountActive());
        product.setProductId(productMessage.getProductId());
        product.setProductCategoryId(productMessage.getProductCategoryId());
        product.setProductInventoryId(productMessage.getProductInventoryId());
        product.setDiscountId(productMessage.getDiscountId());
        productRepository.save(product);
        return true;
    }
}