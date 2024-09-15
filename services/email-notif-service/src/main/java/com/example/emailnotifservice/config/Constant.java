package com.example.emailnotifservice.config;

public class Constant {

    public final class ControllersMapping{
        public static final String AUTH = "/email";
    }

    public final class ApisMapping{
        public static final String REGISTER_NEW_USER = "/registerEmail";

    }



    public final class Client{

        public static final String USER_SERVICE_NAME = "USER-SERVICE";
        public static final String GET_USER_BY_USER_NAME = "/getuser/{username}";
    }

    public final class OrderStatusEmailController{
        public static final String ORDER_STATUS_EMAIL_API = "/email/order";

        public static final String SEND_COMPLETED_ORDER_EMAIL = "/completedOrder";
        public static final String SEND_FAILED_ORDER_EMAIL = "/failedOrder";

    }
}
