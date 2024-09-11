package com.abdelrahman.orderservice.constant;

public class Constant {

    public final  class OrderController{
        public static final String ORDER = "/orders/order";
        public static final String ADD_ORDER = "/add";
        public static final String UPDATE_ORDER = "/update/{order_id}";
        public static final String GET_ORDER = "/get/{order_id}";
    }


    public final class KafkaConst{
        public static final String KAFKA_ORDER_CREATED_TOPIC_NAME = "orderCreatedTopic";
        public static final String KAFKA_INVENTORY_FAILED_TOPIC_NAME = "inventoryFailedTopic";


        public static final  String BOOTSTRAP_SERVERS_URL = "localhost:9092";
        public static final  String ORDER_GROUP_ID = "orderGroup";
    }
}
