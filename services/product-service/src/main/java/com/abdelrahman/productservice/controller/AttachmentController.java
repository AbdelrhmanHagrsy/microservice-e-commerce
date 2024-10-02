package com.abdelrahman.productservice.controller;

import com.abdelrahman.productservice.exception.EntityNotFound;
import com.abdelrahman.productservice.service.AttachmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.abdelrahman.productservice.constant.Constant.AttachmentController.*;

@RestController
@RequestMapping(ATTACHMENT_PATH)
@RequiredArgsConstructor
@Slf4j
public class AttachmentController {
    private final AttachmentService attachmentService;

    @PostMapping(UPLOAD_PRODUCT_IMAGE)
    public ResponseEntity<String> uploadProductImage(@PathVariable(name = "product_id") Long product_id, @RequestParam(name = "image", required = true) MultipartFile image) {
        try {

            return ResponseEntity.ok().body(attachmentService.uploadProductImage(product_id, image));

        } catch (EntityNotFound e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping(DELETE_PRODUCT_IMAGE)
    public ResponseEntity<String> deleteProductImage(@PathVariable(name = "aws_image_name") String awsImageName){
        try {

            return ResponseEntity.ok().body(attachmentService.deleteProductImage(awsImageName));

        } catch (EntityNotFound e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }



}
