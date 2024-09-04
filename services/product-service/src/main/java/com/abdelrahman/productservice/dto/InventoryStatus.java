package com.abdelrahman.productservice.dto;

public enum InventoryStatus {

    AVAILABLE,      // Product is in stock and available for purchase
    RESERVED,       // Product is reserved for a customer but not yet sold
    OUT_OF_STOCK,   // Product is completely out of stock
    PENDING_RESTOCK, // Product is out of stock but more is expected
    DISCONTINUED,   // Product is no longer available for sale
    DAMAGED         // Product is damaged and not available for sale
}
