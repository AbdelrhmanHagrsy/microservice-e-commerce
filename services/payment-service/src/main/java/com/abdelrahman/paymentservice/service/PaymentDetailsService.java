package com.abdelrahman.paymentservice.service;

import com.abdelrahman.paymentservice.dto.kafka.CustomerPaymentDto;
import com.abdelrahman.paymentservice.dto.kafka.OrderCreatedMessage;
import com.abdelrahman.paymentservice.dto.kafka.ReservedInventoryOrderMessage;
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

    public void handleOrderPaymentProcess(OrderCreatedMessage orderCreatedMessage){

        try {
            // check payment method is available for customer
            CustomerPayment customerAddress = customerPaymentRepository.findByIdAndCustomerId(orderCreatedMessage.getPaymentId(),orderCreatedMessage.getCustomerId())
                    .orElseThrow(() -> new EntityNotFound(String.format("Customer Payment with id: %s Not Found", orderCreatedMessage.getPaymentId())));
            // payment process

            // create payment
            paymentDetailsRepository.save(
                    PaymentDetails.builder()
                            .orderId(orderCreatedMessage.getOrderId())
                            .amount(orderCreatedMessage.getTotal())
                            .provider(customerAddress.getProvider())
                            .status(true)
                            .build()
            );

        }catch (Exception ex){
            throw  new PaymentFailedException(String.format("Payment for order id %s Failed %s",orderCreatedMessage.getOrderId(),ex.getMessage()));
        }
    }
}
