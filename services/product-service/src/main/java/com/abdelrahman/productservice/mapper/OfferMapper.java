package com.abdelrahman.productservice.mapper;

import com.abdelrahman.productservice.dto.OfferDto;
import com.abdelrahman.productservice.entity.Offer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OfferMapper {
    private final ProductCategoryMapper productCategoryMapper;

    public OfferDto convertEntityToDto(Offer offer) {
        return OfferDto.builder()
                .message(offer.getMessage())
                .imageName(offer.getImageName())
                .imageContent(offer.getImageContent())
                .isActive(offer.getIsActive())
                .productCategoryDto(productCategoryMapper.convertEntityToDto(offer.getProductCategory()))
                .build();
    }
}
