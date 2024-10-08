package com.abdelrahaman.authenticationservice.constant;


public class Constant {

    public final class ControllersMapping{
        public static final String USER_CONTROLLER = "/auth/user";
    }

    public final class ApisMapping{
        public static final String REGISTER_NEW_USER_API = "/register";
        public static final String LOGIN_API = "/login";
        public static final String CONFIRM_ACCOUNT_API = "/confirmAccount";
        public static final String VALIDATE_TOKEN = "/validateToken";
        public static final String CHANGE_USER_ROLE = "/changeRole/{user_email}";

    }



    public final class Client{

        public static final String USER_SERVICE_NAME = "USER-SERVICE";
        public static final String GET_USER_BY_USER_NAME = "/getuser/{username}";
    }

    public final class EmailNotif{

        public static final String EMAIL_NOTIFY_SERVICE = "EMAIL-NOTIF-SERVICE";
        public static final String SEND_REGISTERATION_EMAIL = "/email/registerEmail";
    }
}
