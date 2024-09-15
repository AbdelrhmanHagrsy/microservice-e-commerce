package com.example.emailnotifservice.service;

import com.example.emailnotifservice.dto.CompletedOrderEmailRequest;
import com.example.emailnotifservice.dto.OrderFailedRequest;
import com.example.emailnotifservice.dto.OrderItemDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import org.springframework.mail.javamail.MimeMessageHelper;

import static com.example.emailnotifservice.constant.EmailTemplate.ORDER_FAILED_EMAIL_BODY;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderStatusEmailService {

    @Autowired
    private JavaMailSender javaMailSender;


    public ResponseEntity<Boolean> sendCompletedOrderEmail(CompletedOrderEmailRequest completedOrderEmailRequest) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, "utf-8");

            String htmlContent = "<html><body>"
                    + "<h1>Completed Order</h1>"
                    + "<p>Dear " + completedOrderEmailRequest.getCustomerUserName() + ",</p>"
                    + "<p>Thank you for your order. Here are the details:</p>"
                    + "<table border='1' cellpadding='5' cellspacing='0'>"
                    + "<tr><th>Item No</th><th>Product Name</th><th>Quantity</th><th>Price</th></tr>";

            int itemCount =0;
            for (OrderItemDto item : completedOrderEmailRequest.getOrderItemDtoList()) {
                htmlContent += "<tr>"
                        + "<td>" + ++itemCount + "</td>"
                        + "<td>" + item.getProductName() + "</td>"
                        + "<td>" + item.getQuantity() + "</td>"
                        + "<td>" + item.getItemPrice() + "</td>"
                        + "</tr>";
            }

            htmlContent += "</table>"
                    + "<p>Total: " + completedOrderEmailRequest.getTotal() + "</p>"
                    + "<p>Thank you for shopping with us!</p>"
                    + "</body></html>";

            messageHelper.setTo(completedOrderEmailRequest.getCustomerUserName());
            messageHelper.setSubject("Completed Order");
            messageHelper.setText(htmlContent, true);

            javaMailSender.send(mimeMessage);
            return ResponseEntity.ok().body(true);
        } catch (MessagingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }

    public ResponseEntity<Boolean> sendFailedOrderEmail(OrderFailedRequest orderFailedRequest) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(orderFailedRequest.getCustomerUserName());
        simpleMailMessage.setSubject("Your Order Has Failed – Action Required");
        String emailBody = String.format(ORDER_FAILED_EMAIL_BODY,orderFailedRequest.getCustomerUserName(),orderFailedRequest.getTotal());
        simpleMailMessage.setText(emailBody);
        javaMailSender.send(simpleMailMessage);
        return ResponseEntity.ok().body(true);
    }
}
