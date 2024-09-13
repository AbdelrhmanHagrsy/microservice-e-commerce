package com.abdelrahman.paymentservice.dto.kafka;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderItemDto {

    @NotBlank
    private String productId;
    @NotNull
    @Positive
    private Integer quantity;
    @NotNull
    @Positive
    private BigDecimal itemPrice;
}
