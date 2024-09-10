package com.abdelrhman.inventoryservice.dto;

public enum InventoryStatus {

    AVAILABLE,      // Product is in stock and available for purchase
    OUT_OF_STOCK,   // Product is completely out of stock
    PENDING_RESTOCK, // Product is out of stock but more is expected
    DISCONTINUED,   // Product is no longer available for sale
}
