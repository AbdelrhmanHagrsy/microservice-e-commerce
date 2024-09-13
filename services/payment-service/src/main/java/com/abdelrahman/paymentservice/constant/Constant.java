package com.abdelrahman.paymentservice.constant;

public class Constant {

    public final class CustomerPaymentController{
        public static final String CUSTOMER_PAYMENT_CONTROLLER = "/payments/payment";
        public static final String ADD_CUSTOMER_PAYMENT = "/addCustomerPayment";
        public static final String UPDATE_CUSTOMER_PAYMENT = "/updateCustomerPayment/{customerPayment_id}";
        public static final String GET_CUSTOMER_PAYMENT = "/getCustomerPayment/{customerPayment_id}";
    }

    public final class KafkaConstant{
        public static final  String BOOTSTRAP_SERVERS_URL = "localhost:9092";
        public static final  String PAYMENT_GROUP_ID = "paymentGroup";
        public static final String KAFKA_INVENTORY_RESERVATION_MESSAGES_TOPIC  = "inventoryReservationTopic";
        public static final String KAFKA_ORDER_PAYMENT_TOPIC_NAME = "orderPaymentTopic";

    }
}
