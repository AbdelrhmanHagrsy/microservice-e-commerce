package com.abdelrahman.productservice.service;

import com.abdelrahman.productservice.dto.ProductMessageStatus;
import com.abdelrahman.productservice.dto.kafka.ProductMessage;
import com.abdelrahman.productservice.dto.request.ProductRequest;
import com.abdelrahman.productservice.entity.Discount;
import com.abdelrahman.productservice.entity.Product;
import com.abdelrahman.productservice.entity.ProductCategory;
import com.abdelrahman.productservice.exception.EntityAlreadyExist;
import com.abdelrahman.productservice.exception.EntityNotFound;
import com.abdelrahman.productservice.mapper.ProductMapper;
import com.abdelrahman.productservice.repository.DiscountRepository;
import com.abdelrahman.productservice.repository.ProductCategoryRepository;
import com.abdelrahman.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.util.logging.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.abdelrahman.productservice.constant.Constant.KafkaConst.KAFKA_PRODUCT_TOPIC_NAME;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final DiscountRepository discountRepository;

    private final ProductMapper productMapper;
    private final KafkaTemplate<String, ProductMessage> kafkaTemplate;

    public String addProduct(ProductRequest productRequest) {

        try {
            // Validation
            if (productRepository.findBySku(productRequest.getSku()).isPresent()) {
                throw new EntityAlreadyExist(String.format("A product with SKU '%s' already exists. Please use a unique SKU.", productRequest.getSku()));
            }
            if (productRequest.getProductCategoryDto() == null || productRequest.getProductCategoryDto().getId() == null) {
                throw new EntityNotFound("Product category not found or missing required ID.");
            }
            // Convert DTO to Entity
            Product product = productMapper.convertProductDtoToEntity(productRequest);
            // Check if Product Category exist
            ProductCategory productCategory = productCategoryRepository.findById(productRequest.getProductCategoryDto().getId())
                    .orElseThrow(() -> new EntityNotFound(String.format("Product category with ID %s not found.", productRequest.getProductCategoryDto().getId())));
            product.setProductCategory(productCategory);

            // Set Discount if provided
            if (productRequest.getDiscountDto() != null) {
                Discount discount = discountRepository.findById(productRequest.getDiscountDto().getId())
                        .orElseThrow(() -> new EntityNotFound(String.format("Discount with ID %s not found.", productRequest.getDiscountDto().getId())));
                product.setDiscount(discount);
            }
            // Save the Product
            product = productRepository.save(product);

            // Send product to NoSQL service via Kafka
            kafkaTemplate.send(KAFKA_PRODUCT_TOPIC_NAME, productMapper.prepareProductMessage(product,ProductMessageStatus.CREATE));

            return "Product added successfully.";

        } catch (EntityNotFound | EntityAlreadyExist exception) {
            throw exception;
        } catch (Exception exception) {
            log.error("Error while adding product",exception.getMessage());
            throw new RuntimeException("An unexpected error occurred while adding the product.");
        }
    }


    public String updateProduct(Long productId, ProductRequest productRequest) {

        try {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new EntityNotFound(String.format("Product with ID %s not found.", productId)));

            if (productRequest.getProductCategoryDto() != null) {
                ProductCategory productCategory = productCategoryRepository.findById(productRequest.getProductCategoryDto().getId())
                        .orElseThrow(() -> new EntityNotFound(String.format("Product category with ID %s not found.", productRequest.getProductCategoryDto().getId())));
                product.setProductCategory(productCategory);
            }
            if (productRequest.getDiscountDto() != null) {
                Discount discount = discountRepository.findById(productRequest.getDiscountDto().getId())
                        .orElseThrow(() -> new EntityNotFound(String.format("Discount with ID %s not found.", productRequest.getDiscountDto().getId())));
                product.setDiscount(discount);
            }
            product = productMapper.updateProductInformation(product, productRequest);

            // Save the Product
            productRepository.save(product);

            // Send product to NoSQL service via Kafka
            kafkaTemplate.send(KAFKA_PRODUCT_TOPIC_NAME, productMapper.prepareProductMessage(product, ProductMessageStatus.UPDATE));

            return "Product updated successfully.";

        } catch (EntityNotFound exception) {
            throw  exception;
        } catch (Exception exception) {
            log.error("Error while updating product :" + exception.getMessage());
            throw new RuntimeException("An unexpected error occurred while updating the product.");
        }
    }




}
