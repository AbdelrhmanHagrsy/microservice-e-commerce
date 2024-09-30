package com.abdelrahman.productservice.service;

import com.abdelrahman.productservice.dto.OfferDto;
import com.abdelrahman.productservice.dto.OfferRequest;
import com.abdelrahman.productservice.dto.ProductCategoryDto;
import com.abdelrahman.productservice.entity.Offer;
import com.abdelrahman.productservice.entity.ProductCategory;
import com.abdelrahman.productservice.exception.EntityNotFound;
import com.abdelrahman.productservice.mapper.OfferMapper;
import com.abdelrahman.productservice.repository.OfferRepository;
import com.abdelrahman.productservice.repository.ProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.abdelrahman.productservice.constant.Constant.OfferController.OFFER_SAVED;

@Service
@RequiredArgsConstructor
@Slf4j
public class OfferService {
    private final OfferRepository offerRepository;

    private final ProductCategoryRepository productCategoryRepository;
    private final OfferMapper offerMapper;

    public String addOffer(OfferRequest offerDto, MultipartFile image){
        try {
            ProductCategory productCategory = productCategoryRepository.findById(offerDto.getProductCategoryId().longValue())
                    .orElseThrow(() -> new EntityNotFound(String.format("Product category with ID %s not found.", offerDto.getProductCategoryId())));

            String imageContent = new String(image.getBytes(), StandardCharsets.UTF_8); // or Base64 if preferred

            Offer offer = Offer.builder()
                    .message(offerDto.getMessage())
                    .imageName(image.getOriginalFilename())
                    .imageContent(imageContent)
                    .productCategory(productCategory)
                    .isActive(false) // new offer is not active by default
                    .build();
            offerRepository.save(offer);
            log.info("New offer saved successfully");
            return OFFER_SAVED;
        }catch (EntityNotFound e){
            log.error(e.getMessage());
            throw e;
        }catch (Exception e){
            throw new RuntimeException("Failed to save the new offer !");
        }

    }

    public void deleteOffer(Long offerId){
        try {
            Offer offer = offerRepository.findById(offerId)
                    .orElseThrow(() -> new EntityNotFound(String.format("Offer with ID %s not found.", offerId)));
            offerRepository.deleteById(offerId);

        }catch (EntityNotFound e){
            log.error(e.getMessage());
            throw e;
        }catch (Exception e){
            log.error(e.getMessage());
            throw new RuntimeException("Failed to delete offer !");
        }

    }

    public void changeOfferStatus(Long offerId,Boolean status){
        try {
        Offer offer = offerRepository.findById(offerId)
                .orElseThrow(() -> new EntityNotFound(String.format("Offer with ID %s not found.", offerId)));
        offer.setIsActive(status);
        offerRepository.save(offer);
        }catch (EntityNotFound e){
            log.error(e.getMessage());
            throw e;
        }catch (Exception e){
            log.error(e.getMessage());
            throw new RuntimeException("Failed to change offer activity !");
        }
    }

    @Transactional
    public List<OfferDto> getAllActiveOrUnActiveOfferByCategory(Boolean status,Long productCategoryId){
        try {
            ProductCategory productCategory = productCategoryRepository.findById(productCategoryId)
                    .orElseThrow(() -> new EntityNotFound(String.format("Product category with ID %s not found.", productCategoryId)));

            List<Offer> offers = offerRepository.findAllByIsActiveAndProductCategory_Id(status,productCategory.getId());
          //  List<Offer> offers = offerRepository.findAll();

            return offers.stream().map(offerMapper::convertEntityToDto).collect(Collectors.toList());

        }catch (EntityNotFound e){
            log.error(e.getMessage());
            throw e;
        }catch (Exception e){
            log.error(e.getMessage());
            throw new RuntimeException("Failed to fetch offers !");
        }
    }

    @Transactional
    public List<OfferDto> getAllActiveOffer(){
            List<Offer> offers = offerRepository.findAllByIsActive(true);
            return offers.stream().map(offerMapper::convertEntityToDto).collect(Collectors.toList());
    }
}
