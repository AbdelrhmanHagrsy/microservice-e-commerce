package com.abdelrhman.inventoryservice.service;

import com.abdelrhman.inventoryservice.dto.OrderItemDto;
import com.abdelrhman.inventoryservice.dto.kafka.OrderCreatedMessage;
import com.abdelrhman.inventoryservice.entity.ProductInventory;
import com.abdelrhman.inventoryservice.exception.QuantityNotAvailable;
import com.abdelrhman.inventoryservice.repository.ProductInventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductInventoryService {

    private final ProductInventoryRepository productInventoryRepository;

    @Transactional
    public void deductOrderQuantityFromInventory(OrderCreatedMessage orderCreatedMessage) {
        for(OrderItemDto orderItemDto : orderCreatedMessage.getOrderItemDtoList()){
            ProductInventory productInventory = productInventoryRepository.findById(orderItemDto.getProductId()).get();
            Integer availableQuantity = productInventory.getAvailableQuantity();
            if(availableQuantity == 0 || orderItemDto.getQuantity() > availableQuantity)
                throw new QuantityNotAvailable("Quantity for product id:"+orderItemDto.getProductId()+" not available");
            //
            productInventory.setAvailableQuantity(availableQuantity-orderItemDto.getQuantity());
            productInventoryRepository.save(productInventory);
        }
    }
}
