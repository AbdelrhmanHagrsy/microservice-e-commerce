package com.abdelrhman.inventoryservice.service;

import com.abdelrhman.inventoryservice.repository.ProductInventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductInventoryService {

    private final ProductInventoryRepository productInventoryRepository;

}
