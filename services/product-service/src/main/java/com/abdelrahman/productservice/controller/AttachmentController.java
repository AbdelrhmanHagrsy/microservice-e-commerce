package com.abdelrahman.productservice.controller;

import com.abdelrahman.productservice.dto.UserRoles;
import com.abdelrahman.productservice.exception.EntityNotFound;
import com.abdelrahman.productservice.service.AttachmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
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
    public ResponseEntity<String> uploadProductImage(
            @RequestHeader(value = "X-User-Roles", required = true) String userRole,
            @PathVariable(name = "product_id") Long product_id, @RequestParam(name = "image", required = true) MultipartFile image) {
        try {
            if (userRole.equals(UserRoles.SUPER_ADMIN.name()) || userRole.equals(UserRoles.PRODUCT_MANAGER.name())) {
                return ResponseEntity.ok().body(attachmentService.uploadProductImage(product_id, image));
            } else {
                return ResponseEntity.status(HttpStatus.SC_UNAUTHORIZED).body("You do not have permission to update product.");
            }

        } catch (EntityNotFound e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping(DELETE_PRODUCT_IMAGE)
    public ResponseEntity<String> deleteProductImage(
            @RequestHeader(value = "X-User-Roles", required = true) String userRole,
            @PathVariable(name = "aws_image_name") String awsImageName) {
        try {
            if (userRole.equals(UserRoles.SUPER_ADMIN.name()) || userRole.equals(UserRoles.PRODUCT_MANAGER.name())) {
                return ResponseEntity.ok().body(attachmentService.deleteProductImage(awsImageName));
            } else {
                return ResponseEntity.status(HttpStatus.SC_UNAUTHORIZED).body("You do not have permission to update product.");
            }

        } catch (EntityNotFound e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

}
