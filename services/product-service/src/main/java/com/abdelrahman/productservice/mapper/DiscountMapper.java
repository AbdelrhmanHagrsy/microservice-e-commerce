package com.abdelrahman.productservice.mapper;

import com.abdelrahman.productservice.dto.DiscountDto;
import com.abdelrahman.productservice.entity.Discount;
import org.springframework.stereotype.Component;

@Component
public class DiscountMapper {

    public Discount convertDtoToEntity(DiscountDto discountDto){
        return Discount.builder()
                .id(discountDto.getId())
                .name(discountDto.getDiscountName())
                .desc(discountDto.getDescription())
                .active(discountDto.getActive())
                .discountPercent(discountDto.getDiscountPercent())
                .build();
    }

    public DiscountDto convertEntityToDto(Discount discount){
        return DiscountDto.builder()
                .id(discount.getId())
                .discountName(discount.getName())
                .description(discount.getDesc())
                .active(discount.getActive())
                .discountPercent(discount.getDiscountPercent())
                .build();
    }
}
