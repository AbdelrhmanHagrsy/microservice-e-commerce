package com.abdelrahman.paymentservice.service;

import com.abdelrahman.paymentservice.dto.kafka.*;
import com.abdelrahman.paymentservice.entity.CustomerPayment;
import com.abdelrahman.paymentservice.entity.PaymentDetails;
import com.abdelrahman.paymentservice.exception.EntityNotFound;
import com.abdelrahman.paymentservice.exception.PaymentFailedException;
import com.abdelrahman.paymentservice.repository.CustomerPaymentRepository;
import com.abdelrahman.paymentservice.repository.PaymentDetailsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentDetailsService {

    private  final PaymentDetailsRepository paymentDetailsRepository;
    private final CustomerPaymentRepository customerPaymentRepository;

    public PaymentStatus handleOrderPaymentProcess(OrderCreatedMessage orderCreatedMessage){

        try {
            // check payment method is available for customer
            CustomerPayment customerAddress = customerPaymentRepository.findByIdAndCustomerId(orderCreatedMessage.getPaymentId(),orderCreatedMessage.getCustomerId())
                    .orElseThrow(() -> new EntityNotFound(String.format("Customer Payment with id: %s Not Found", orderCreatedMessage.getPaymentId())));
            PaymentDetails paymentDetails = PaymentDetails.builder()
                    .orderId(orderCreatedMessage.getOrderId())
                    .amount(orderCreatedMessage.getTotal())
                    .paymentStatus(PaymentStatus.PENDING)
                    .build();
            // payment process
            if(customerAddress.getPaymentType().equals(PaymentType.CASH)){
                paymentDetails.setPaymentStatus(PaymentStatus.AWAITING_COD);
            }
            if(customerAddress.getPaymentType().equals(PaymentType.CREDIT_CARD)){
                // handel payment with credit card
                paymentDetails.setPaymentStatus(PaymentStatus.COMPLETED);
            }
            // create payment
            paymentDetailsRepository.save(paymentDetails);
            return paymentDetails.getPaymentStatus();

        }catch (Exception ex){
            throw  new PaymentFailedException(String.format("Payment for order id %s Failed %s",orderCreatedMessage.getOrderId(),ex.getMessage()));
        }
    }
}
