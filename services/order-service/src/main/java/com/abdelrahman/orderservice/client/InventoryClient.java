package com.abdelrahman.orderservice.client;

import com.abdelrahman.orderservice.dto.kafka.CancelOrderInventoryDeductionRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.abdelrahman.orderservice.constant.Constant.InventoryClientConstant.*;

@FeignClient(name = INVENTORY_SERVICE_NAME)
public interface InventoryClient {

    @PutMapping(CANCEL_ORDER_PRODUCT_INVENTORY_DEDUCTION)
    public ResponseEntity<Boolean> cancelOrderProductInventoryDeduction(@RequestBody CancelOrderInventoryDeductionRequest cancelOrderInventoryDeductionRequest);
}
