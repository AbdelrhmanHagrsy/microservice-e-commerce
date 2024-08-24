package com.abdelrhman.productnosqlservice.controller;

import com.abdelrhman.productnosqlservice.entity.Product;
import com.abdelrhman.productnosqlservice.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class productController {

    private final ProductRepository productRepository;


}
