package com.abdelrahman.productservice.controller;

import com.abdelrahman.productservice.dto.OfferDto;
import com.abdelrahman.productservice.dto.OfferRequest;
import com.abdelrahman.productservice.dto.ProductCategoryDto;
import com.abdelrahman.productservice.dto.UserRoles;
import com.abdelrahman.productservice.dto.request.ProductRequest;
import com.abdelrahman.productservice.exception.EntityNotFound;
import com.abdelrahman.productservice.service.OfferService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.hc.client5.http.entity.mime.MultipartPart;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.abdelrahman.productservice.constant.Constant.OfferController.*;

@RestController
@RequestMapping(PRODUCTS_OFFER)
@RequiredArgsConstructor
public class OfferController {
    private final OfferService offerService;
    private static final ObjectMapper objectMapper = new ObjectMapper();


    // POST method to handle offer submission with OfferDto and image
    @PostMapping(path = ADD_OFFER, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> addOffer(
            @RequestHeader(value = "X-User-Roles", required = true) String userRole,
            @RequestPart("offerRequest")
            String offerRequest,      // Accepts JSON as part of multipart
            @RequestPart("image") MultipartFile image) {  // Accepts image file
        try {
            if (userRole.equals(UserRoles.SUPER_ADMIN.name()) || userRole.equals(UserRoles.PRODUCT_MANAGER.name())) {
                OfferRequest offer = objectMapper.readValue(offerRequest, OfferRequest.class);
                // Call service to handle the logic
                String response = offerService.addOffer(offer, image);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                return ResponseEntity.status(org.apache.http.HttpStatus.SC_UNAUTHORIZED).body("You do not have permission to update product.");
            }

        } catch (EntityNotFound e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


    @DeleteMapping(DELETE_OFFER)
    public ResponseEntity<Void> deleteOffer(
            @RequestHeader(value = "X-User-Roles", required = true) String userRole,
            @PathVariable(name = "offer_id") Long offerId){
        try {
            if (userRole.equals(UserRoles.SUPER_ADMIN.name()) || userRole.equals(UserRoles.PRODUCT_MANAGER.name())) {
                offerService.deleteOffer(offerId);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(org.apache.http.HttpStatus.SC_UNAUTHORIZED).build();
            }

        }catch (EntityNotFound e){
            return ResponseEntity.badRequest().build();
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping(CHANGE_OFFER_ACTIVITY_STATUS)
    public ResponseEntity<Void> changeOfferActivity(
            @RequestHeader(value = "X-User-Roles", required = true) String userRole,
            @PathVariable(name = "offer_id") Long offerId, @RequestParam(name = "status") Boolean status) {
        try {

            if (userRole.equals(UserRoles.SUPER_ADMIN.name()) || userRole.equals(UserRoles.PRODUCT_MANAGER.name())) {
                offerService.changeOfferStatus(offerId, status);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(org.apache.http.HttpStatus.SC_UNAUTHORIZED).build();
            }
        } catch (EntityNotFound e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }


    @GetMapping(GET_ALL_OFFER_BY_CATEGORY_AND_STATUS)
    public ResponseEntity<List<OfferDto>> getAllActiveOfferByCategory(@RequestParam(name = "status") Boolean status, @RequestParam(name = "categoryId") Long productCategoryId){
        try {

            return ResponseEntity.ok().body(offerService.getAllActiveOrUnActiveOfferByCategory(status,productCategoryId));

        }catch (EntityNotFound e){
            return ResponseEntity.badRequest().build();
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }
}
