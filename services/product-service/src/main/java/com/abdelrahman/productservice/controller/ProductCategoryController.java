package com.abdelrahman.productservice.controller;

import com.abdelrahman.productservice.dto.ProductCategoryDto;
import com.abdelrahman.productservice.dto.UserRoles;
import com.abdelrahman.productservice.exception.EntityNotFound;
import com.abdelrahman.productservice.service.ProdcutCategoryService;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.abdelrahman.productservice.constant.Constant.ProductCategoryController.*;


@RestController
@RequestMapping(PRODUCTS_CATEGORY)
@RequiredArgsConstructor
public class ProductCategoryController {
    private final ProdcutCategoryService prodcutCategoryService;


    @PostMapping(ADD_PRODUCT_CATEGORY)
    ResponseEntity<String> addProductategory(
            @RequestHeader(value = "X-User-Roles", required = true) String userRole,
            @RequestBody ProductCategoryDto productCategoryDto){
        try {
            if (userRole.equals(UserRoles.SUPER_ADMIN.name()) || userRole.equals(UserRoles.PRODUCT_MANAGER.name())){
                return  ResponseEntity.status(HttpStatus.SC_CREATED).body(prodcutCategoryService.addProductCategory(productCategoryDto));
            }else{
                return ResponseEntity.status(HttpStatus.SC_UNAUTHORIZED).body("You do not have permission to update product.");
            }
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }

    @PutMapping(UPDATE_PRODUCT_CATEGORY)
    ResponseEntity<String> addProductategory(
            @RequestHeader(value = "X-User-Roles", required = true) String userRole,
            @PathVariable(name = "category_id") Long id, @RequestBody ProductCategoryDto productCategoryDto) {

        try {
            if (userRole.equals(UserRoles.SUPER_ADMIN.name()) || userRole.equals(UserRoles.PRODUCT_MANAGER.name())) {
                return ResponseEntity.ok().body(prodcutCategoryService.updateProductCategory(id, productCategoryDto));
            } else {
                return ResponseEntity.status(HttpStatus.SC_UNAUTHORIZED).body("You do not have permission to update product.");
            }
        } catch (EntityNotFound exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping(GET_PRODUCT_CATEGORY)
    ResponseEntity<?> getProductCategory(
            @RequestHeader(value = "X-User-Roles", required = true) String userRole,
            @PathVariable(name = "category_id") Long id) {

        try {
            if (userRole.equals(UserRoles.SUPER_ADMIN.name()) || userRole.equals(UserRoles.PRODUCT_MANAGER.name())) {
                return ResponseEntity.ok().body(prodcutCategoryService.getProductCategory(id));
            } else {
                return ResponseEntity.status(HttpStatus.SC_UNAUTHORIZED).body("You do not have permission to update product.");
            }
        } catch (EntityNotFound exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
