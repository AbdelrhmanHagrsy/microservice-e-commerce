package com.abdelrahman.productservice.service;

import com.abdelrahman.productservice.dto.DiscountDto;
import com.abdelrahman.productservice.dto.ProductCategoryDto;
import com.abdelrahman.productservice.entity.Discount;
import com.abdelrahman.productservice.entity.ProductCategory;
import com.abdelrahman.productservice.exception.EntityNotFound;
import com.abdelrahman.productservice.mapper.DiscountMapper;
import com.abdelrahman.productservice.repository.DiscountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DiscountService {

    private final DiscountRepository discountRepository;
    private final DiscountMapper discountMapper;
    public ResponseEntity<String> addDiscount(DiscountDto discountDto) {
        try {
            Discount discount = discountMapper.convertDtoToEntity(discountDto);
            discountRepository.save(discount);
            return ResponseEntity.ok("Discount added successfully.");

        } catch (Exception exception) {
            log.error("Error while adding Discount", exception.getMessage());
            return ResponseEntity.internalServerError().body("An unexpected error occurred while adding the discount.");
        }
    }

    public ResponseEntity<String> updateDiscount(Long discountId, DiscountDto discountDto) {
        try {

            Discount discount = discountRepository.findById(discountId)
                    .orElseThrow(() -> new EntityNotFound(String.format("Discount with ID %s not found.", discountId)));
            //
            discount.setName(discountDto.getDiscountName());
            discount.setDesc(discountDto.getDescription());
            discount.setActive(discountDto.getActive());
            discount.setDiscountPercent(discount.getDiscountPercent());
            discountRepository.save(discount);
            return ResponseEntity.ok("Discount updated successfully.");

        } catch (EntityNotFound exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        } catch (Exception exception) {
            log.error("Error while updating Discount", exception.getMessage());
            return ResponseEntity.internalServerError().body("An unexpected error occurred while updating the Discount.");
        }
    }

    public ResponseEntity<?> getDiscount(Long discountId) {
        try {

            Discount discount = discountRepository.findById(discountId)
                    .orElseThrow(() -> new EntityNotFound(String.format("Discount with ID %s not found.", discountId)));
            //
            DiscountDto discountDto = discountMapper.convertEntityToDto(discount);
            return  ResponseEntity.ok().body(discountDto);

        } catch (EntityNotFound exception) {
            log.error("Error while get Discount", exception.getMessage());
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }
}
