package com.abdelrahman.customerservice.constant;

public class Constant {

    public final class CustomerController{
        public static final String CUSTOMER_CONTROLLER = "/customers/customer";
        public static final String GET_CUSTOMER = "/getCustomer/{customer_id}";


    }

    public final class CustomerAddressController{
        public static final String CUSTOMER_ADDRESS_CONTROLLER = "/customers/address";
        public static final String ADD_CUSTOMER_ADDRESS = "/addCustomerAddress/{customer_id}";
        public static final String UPDATE_CUSTOMER_ADDRESS = "/updateCustomerAddress/{customerAddress_id}";
        public static final String GET_CUSTOMER_ADDRESS = "/getCustomerAddress/{customerAddress_id}";

    }


}
