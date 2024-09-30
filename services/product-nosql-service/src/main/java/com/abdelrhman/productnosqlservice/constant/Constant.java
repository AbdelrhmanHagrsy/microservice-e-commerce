package com.abdelrhman.productnosqlservice.constant;

public class Constant {

    public final class KafkaConstant{
        public static final  String BOOTSTRAP_SERVERS_URL = "localhost:9092";
        public static final  String PRODUCT_GROUP_ID = "productGroup";
        public static final String KAFKA_PRODUCT_TOPIC_NAME = "productTopic";


    }

    public final class ProductController{

        public static final String PRODUCT_PATH = "/noSqlProducts";
        public static final String GET_ALL_PRODUCT_WITH_FILTERS = "/getAllFilters";

    }
}
