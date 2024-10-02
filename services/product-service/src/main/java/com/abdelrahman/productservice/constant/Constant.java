package com.abdelrahman.productservice.constant;

public class Constant {


    public final class ProductController {
        public static final String PRODUCTS_PRODUCT = "/products/product";
        public static final String ADD_PRODUCT = "/add";
        public static final String UPDATE_PRODUCT = "/update/{product_id}";

    }

    public final class ProductCategoryController {
        public static final String PRODUCTS_CATEGORY = "/products/category";
        public static final String ADD_PRODUCT_CATEGORY = "/add";
        public static final String UPDATE_PRODUCT_CATEGORY = "/update/{category_id}";
        public static final String GET_PRODUCT_CATEGORY = "/get/{category_id}";

    }

    public final  class DiscountController{
        public static final String PRODUCTS_DISCOUNT = "/products/discount";
        public static final String ADD_DISCOUNT = "/add";
        public static final String UPDATE_DISCOUNT = "/update/{discount_id}";
        public static final String GET_DISCOUNT = "/get/{discount_id}";
    }

    public final  class ProductInventoryController{
        public static final String PRODUCTS_INVENTORY = "/products/inventory";
        public static final String ADD_INVENTORY = "/add";
        public static final String UPDATE_INVENTORY = "/update/{inventory_id}";
        public static final String GET_INVENTORY = "/get/{inventory_id}";
    }

    public final  class OfferController{
        public static final String PRODUCTS_OFFER = "/products/offer";
        public static final String ADD_OFFER = "/add";
        public static final String DELETE_OFFER = "/delete/{offer_id}";
        public static final String CHANGE_OFFER_ACTIVITY_STATUS = "/changeStatus/{offer_id}";

        public static final String UPDATE_OFFER = "/update/{offer_id}";
        public static final String GET_ALL_OFFER_BY_CATEGORY_AND_STATUS = "/getAll";
        public static String OFFER_SAVED = "New offer saved successfully";
    }

    public final class AttachmentController {
        public static final String ATTACHMENT_PATH = "/products/attachment";
        public static final String UPLOAD_PRODUCT_IMAGE = "/upload/{product_id}";
        public static final String DELETE_PRODUCT_IMAGE = "/delete/{aws_image_name}";

    }

    public final class KafkaConst{
        public static final String KAFKA_PRODUCT_TOPIC_NAME = "productTopic";
        public static final String KAFKA_ORDER_CREATED_TOPIC_NAME = "orderCreatedTopic";
        public static final String KAFKA_INVENTORY_REVERSED_TOPIC_NAME = "inventoryReservedTopic";
        public static final String KAFKA_INVENTORY_FAILED_TOPIC_NAME = "inventoryFailedTopic";


        public static final  String BOOTSTRAP_SERVERS_URL = "localhost:9092";
        public static final  String PRODUCT_GROUP_ID = "productGroup";
    }

}
