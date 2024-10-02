package com.abdelrhman.inventoryservice.service;

import com.abdelrhman.inventoryservice.dto.CancelOrderInventoryDeductionRequest;
import com.abdelrhman.inventoryservice.dto.InventoryStatus;
import com.abdelrhman.inventoryservice.dto.OrderItemDto;
import com.abdelrhman.inventoryservice.dto.ProductInventoryDto;
import com.abdelrhman.inventoryservice.dto.kafka.OrderCreatedMessage;
import com.abdelrhman.inventoryservice.entity.ProductInventory;
import com.abdelrhman.inventoryservice.exception.EntityAlreadyExist;
import com.abdelrhman.inventoryservice.exception.EntityNotFound;
import com.abdelrhman.inventoryservice.exception.QuantityNotAvailable;
import com.abdelrhman.inventoryservice.repository.ProductInventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductInventoryService {

    private final ProductInventoryRepository productInventoryRepository;

    private void validateProductInventory(ProductInventoryDto productInventoryDto){
        if( productInventoryDto.getInventoryStatus() == null || productInventoryDto.getInventoryStatus().name().isEmpty())
            throw new RuntimeException("Inventory status can't be empty !");
        if(productInventoryDto.getTotalQuantity() != (productInventoryDto.getDamagedQuantity()+productInventoryDto.getAvailableQuantity()))
            throw new RuntimeException(String.format("Invalid total quantity : %s", productInventoryDto.getTotalQuantity()));

        if (productInventoryDto.getAvailableQuantity() > 0 && (productInventoryDto.getInventoryStatus().equals(InventoryStatus.OUT_OF_STOCK)
                || productInventoryDto.getInventoryStatus().equals(InventoryStatus.PENDING_RESTOCK)))
            throw new RuntimeException(String.format("Invalid inventory status: %s", InventoryStatus.OUT_OF_STOCK));

        if (productInventoryDto.getAvailableQuantity() == 0 && productInventoryDto.getInventoryStatus().equals(InventoryStatus.AVAILABLE))
            throw new RuntimeException(String.format("Invalid inventory status: %s", InventoryStatus.AVAILABLE));
    }


    @Transactional
    // using Optimistic Locking to manage concurrent updates
    // in case Optimistic Locking failure thrown  kafka will call this method again and retry to deduct
    public void deductOrderQuantityFromInventory(OrderCreatedMessage orderCreatedMessage) {
        for(OrderItemDto orderItemDto : orderCreatedMessage.getOrderItemDtoList()){
            ProductInventory productInventory = productInventoryRepository.findByProductId(orderItemDto.getProductId()).get();
            Integer availableQuantity = productInventory.getAvailableQuantity();
            //
            if(productInventory.getInventoryStatus().equals(InventoryStatus.DISCONTINUED) || productInventory.getInventoryStatus().equals(InventoryStatus.PENDING_RESTOCK)
                    || productInventory.getInventoryStatus().equals(InventoryStatus.OUT_OF_STOCK))
                throw new QuantityNotAvailable("Product id:"+orderItemDto.getProductId()+" not available now");

            if(availableQuantity == 0 || orderItemDto.getQuantity() > availableQuantity)
                throw new QuantityNotAvailable("Quantity for product id:"+orderItemDto.getProductId()+" not available");
            //
            availableQuantity -= orderItemDto.getQuantity();
            productInventory.setAvailableQuantity(availableQuantity);
            productInventory.setTotalQuantity(availableQuantity + productInventory.getDamagedQuantity());
            productInventory.setInventoryStatus(availableQuantity == 0 ? InventoryStatus.OUT_OF_STOCK : InventoryStatus.AVAILABLE);
            productInventoryRepository.save(productInventory);
        }
    }

    public ResponseEntity<?> addProductInventory(ProductInventoryDto productInventoryDto) {
        try {
            Optional<ProductInventory> productInventory = productInventoryRepository.findByProductId(productInventoryDto.getProductId());
            if (productInventory.isPresent())
                throw new EntityAlreadyExist(String.format("Inventory for product id: %s is already exist!", productInventoryDto.getProductId()));
            ProductInventory createdInventory = productInventoryRepository.save(
                    ProductInventory.builder()
                            .productId(productInventoryDto.getProductId())
                            .availableQuantity(productInventoryDto.getAvailableQuantity())
                            .damagedQuantity(productInventoryDto.getDamagedQuantity())
                            .totalQuantity(productInventoryDto.getTotalQuantity())
                            .inventoryStatus(productInventoryDto.getAvailableQuantity() > 0 ? InventoryStatus.AVAILABLE : InventoryStatus.OUT_OF_STOCK)
                            .location(productInventoryDto.getLocation())
                            .build()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(createdInventory);
        } catch (EntityAlreadyExist ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    public ResponseEntity<?> updateProductInventory(String inventoryId,ProductInventoryDto productInventoryDto) {
        try {
            ProductInventory productInventory = productInventoryRepository.findById(inventoryId)
                    .orElseThrow(() -> new EntityNotFound(String.format("Inventory with id: %s is not exist!", inventoryId)));

            // check inventory status
            validateProductInventory(productInventoryDto);

            productInventory.setInventoryStatus(productInventoryDto.getInventoryStatus());
            productInventory.setLocation(productInventoryDto.getLocation());
            productInventory.setDamagedQuantity(productInventoryDto.getDamagedQuantity());
            productInventory.setAvailableQuantity(productInventoryDto.getAvailableQuantity());
            productInventory.setTotalQuantity(productInventoryDto.getTotalQuantity());
            productInventoryRepository.save(productInventory);

            return ResponseEntity.ok().body(productInventory);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    public ResponseEntity<?> getProductInventory(String inventoryId) {
        try {
            ProductInventory productInventory = productInventoryRepository.findById(inventoryId)
                    .orElseThrow(() -> new EntityNotFound(String.format("Inventory with id: %s is not exist!", inventoryId)));

            return ResponseEntity.ok().body(productInventory);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            log.error("Failed to fetch product inventory {}", ex.getMessage());
            return ResponseEntity.internalServerError().body("Failed to fetch product inventory !");
        }
    }

    public ResponseEntity<Boolean> cancelOrderDeduction(CancelOrderInventoryDeductionRequest cancelOrderInventoryDeductionRequest) {
        for(OrderItemDto orderItemDto : cancelOrderInventoryDeductionRequest.getOrderItemDtoList()){
            ProductInventory productInventory = productInventoryRepository.findByProductId(orderItemDto.getProductId()).get();
            Integer availableQuantity = productInventory.getAvailableQuantity();
            //
            availableQuantity += orderItemDto.getQuantity();
            productInventory.setAvailableQuantity(availableQuantity);
            productInventory.setTotalQuantity(availableQuantity + productInventory.getDamagedQuantity());
            productInventory.setInventoryStatus(availableQuantity == 0 ? InventoryStatus.OUT_OF_STOCK : InventoryStatus.AVAILABLE);
            productInventoryRepository.save(productInventory);
        }
        return ResponseEntity.ok().body(true);
    }
}
