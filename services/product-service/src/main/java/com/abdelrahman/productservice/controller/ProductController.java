package com.abdelrahman.productservice.controller;

import com.abdelrahman.productservice.dto.UserRoles;
import com.abdelrahman.productservice.dto.request.ProductRequest;
import com.abdelrahman.productservice.exception.EntityAlreadyExist;
import com.abdelrahman.productservice.exception.EntityNotFound;
import com.abdelrahman.productservice.service.AttachmentService;
import com.abdelrahman.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.abdelrahman.productservice.constant.Constant.ProductController.*;


@RestController
@RequestMapping(PRODUCTS_PRODUCT)
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping(ADD_PRODUCT)
    public ResponseEntity<String> addProduct(
            @RequestHeader(value = "X-User-Roles", required = true) String userRole,
            @RequestBody ProductRequest productRequest) {
        try {
            // check user role before add a new product
            if (userRole.equals(UserRoles.SUPER_ADMIN.name())  || userRole.equals(UserRoles.PRODUCT_MANAGER.name())){
                return ResponseEntity.ok(productService.addProduct(productRequest));
            }else{
                return ResponseEntity.status(HttpStatus.SC_UNAUTHORIZED).body("You do not have permission to add a new product.");
            }

        } catch (EntityNotFound | EntityAlreadyExist exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred while adding the product.");
        }
    }

    @PutMapping(UPDATE_PRODUCT)
    public ResponseEntity<String> updateProduct(
            @RequestHeader(value = "X-User-Roles", required = true) String userRole,
            @PathVariable(name = "product_id") Long id, @RequestBody ProductRequest productRequest) {

        try {
            // check user role before add a new product
            if (userRole.equals(UserRoles.SUPER_ADMIN.name()) || userRole.equals(UserRoles.PRODUCT_MANAGER.name())) {
                return ResponseEntity.ok().body(productService.updateProduct(id, productRequest));
            } else {
                return ResponseEntity.status(HttpStatus.SC_UNAUTHORIZED).body("You do not have permission to update product.");
            }

        } catch (EntityNotFound exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred while updating the product.");
        }
    }


}
