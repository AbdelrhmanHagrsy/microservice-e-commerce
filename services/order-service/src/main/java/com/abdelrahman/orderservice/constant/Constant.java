package com.abdelrahman.orderservice.constant;

public class Constant {

    public final  class OrderController{
        public static final String ORDER = "/orders/order";
        public static final String ADD_ORDER = "/add";
        public static final String MARK_ORDER_AS_DELIVERED_ORDER = "/markAsDelivered/{order_id}";
        public static final String MARK_ORDER_AS_PAID_ORDER = "/markAsPaid/{order_id}";


        public static final String UPDATE_ORDER = "/update/{order_id}";
        public static final String GET_ORDER = "/get/{order_id}";
        public static final String FETCH_ORDERS_BY_USER_NAME = "/getAll/{userName}";
        public static final String FETCH_CANCELED_ORDERS_BY_USER_NAME = "/getAll/canceled/{userName}";

        public static final String FETCH_COMPLETED_ORDERS_BY_USER_NAME = "/getAll/completed/{userName}";

    }


    public final class KafkaConst{
        public static final String KAFKA_ORDER_CREATED_TOPIC_NAME = "orderCreatedTopic";
        public static final String KAFKA_INVENTORY_FAILED_TOPIC_NAME = "inventoryFailedTopic";

        public static final String KAFKA_ORDER_PAYMENT_TOPIC_NAME = "orderPaymentTopic";

        public static final  String BOOTSTRAP_SERVERS_URL = "localhost:9092";
        public static final  String ORDER_GROUP_ID = "orderGroup";
    }

    public final class EmailNotifyClientConstant{
        public static final String EMAIL_SERVICE_NAME = "EMAIL-NOTIF-SERVICE";
        public static final String SEND_COMPLETED_ORDER_EMAIL = "/email/order/completedOrder";
        public static final String SEND_FAILED_ORDER_EMAIL = "/email/order/failedOrder";


    }

    public final class InventoryClientConstant{
        public static final String INVENTORY_SERVICE_NAME = "INVENTORY-SERVICE";
        public static final String CANCEL_ORDER_PRODUCT_INVENTORY_DEDUCTION = "/inventory/productInventory/cancelOrderDeduction";

    }
}
