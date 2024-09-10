package com.abdelrahman.paymentservice.constant;

public class Constant {

    public final class KafkaConstant{
        public static final  String BOOTSTRAP_SERVERS_URL = "localhost:9092";
        public static final  String PAYMENT_GROUP_ID = "paymentGroup";
        public static final String KAFKA_INVENTORY_REVERSED_TOPIC_NAME = "inventoryReservedTopic";
        public static final String KAFKA_PAYMENT_PROCESSED_TOPIC_NAME = "paymentProcessedTopic";

    }
}
