package com.abdelrhman.productnosqlservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductFilterRequest {

    private int page;
    private int size;
    private Map<String,Object> filters;
}
