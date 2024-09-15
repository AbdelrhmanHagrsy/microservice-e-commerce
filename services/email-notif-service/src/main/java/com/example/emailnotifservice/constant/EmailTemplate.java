package com.example.emailnotifservice.constant;

import org.springframework.stereotype.Component;

@Component
public class EmailTemplate {
    public static final String ORDER_FAILED_EMAIL_BODY =
            "Dear %s,\n\n" +
                    "We’re sorry, but your order totaling %s could not be completed.\n\n" +
                    "What You Can Do:\n" +
                    "- **Check Payment Details:** Verify your payment information.\n" +
                    "- **Retry Your Order:** Place a new order on our website.\n" +
                    "We apologize for the inconvenience and are here to assist you.\n\n" +
                    "Best regards,\n\n" +
                    "Abdelrahman Adel\n" +
                    "Customer Support Team";

}
