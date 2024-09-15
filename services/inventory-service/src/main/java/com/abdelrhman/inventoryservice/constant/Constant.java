package com.abdelrhman.inventoryservice.constant;

public class Constant {



    public final  class ProductInventoryController{
        public static final String PRODUCT_INVENTORY = "/inventory/productInventory";
        public static final String ADD_PRODUCT_INVENTORY = "/add";
        public static final String UPDATE_PRODUCT_INVENTORY = "/update/{inventory_id}";
        public static final String GET_PRODUCT_INVENTORY = "/get/{inventory_id}";
        public static final String CANCEL_ORDER_PRODUCT_INVENTORY_DEDUCTION = "/cancelOrderDeduction";



    }

    public final  class ShoppingSessionController{
        public static final String SHOPPING_SESSION = "/inventory/shoppingSession";
        public static final String ADD_SHOPPING_SESSION = "/add";
        public static final String UPDATE_SHOPPING_SESSION = "/update/{session_id}";
        public static final String GET_SHOPPING_SESSION = "/get/{session_id}";
    }

    public final  class CartItemController{
        public static final String CART_ITEM = "/inventory/cartItem";
        public static final String ADD_CART_ITEM = "/add";
        public static final String UPDATE_CART_ITEM = "/update/{cart_id}";
        public static final String GET_ALL_CART_ITEM = "/getAll/{session_id}";
        public static final String REMOVE_CART_ITEM = "/remove/{cart_id}";
    }

    public final class KafkaConst{
        public static final String KAFKA_ORDER_CREATED_TOPIC_NAME = "orderCreatedTopic";
        public static final String KAFKA_INVENTORY_RESERVATION_MESSAGES_TOPIC  = "inventoryReservationTopic";
        public static final String KAFKA_INVENTORY_FAILED_TOPIC_NAME = "inventoryFailedTopic";
        public static final  String BOOTSTRAP_SERVERS_URL = "localhost:9092";
        public static final  String INVENTORY_GROUP_ID = "inventoryGroup";
    }

}
