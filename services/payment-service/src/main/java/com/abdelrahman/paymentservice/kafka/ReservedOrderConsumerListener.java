package com.abdelrahman.paymentservice.kafka;

import com.abdelrahman.paymentservice.dto.kafka.OrderPaymentMessage;
import com.abdelrahman.paymentservice.dto.kafka.PaymentStatus;
import com.abdelrahman.paymentservice.dto.kafka.ReservedInventoryOrderMessage;
import com.abdelrahman.paymentservice.entity.PaymentTransaction;
import com.abdelrahman.paymentservice.repository.PaymentTransactionRepository;
import com.abdelrahman.paymentservice.service.PaymentDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.abdelrahman.paymentservice.constant.Constant.KafkaConstant.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReservedOrderConsumerListener {

    private final PaymentDetailsService paymentDetailsService;
    private final KafkaTemplate<String, OrderPaymentMessage> messageKafkaTemplate;
    private final PaymentTransactionRepository paymentTransactionRepository;

    @KafkaListener(topics = KAFKA_INVENTORY_RESERVATION_MESSAGES_TOPIC, groupId = PAYMENT_GROUP_ID, containerFactory = "reservedInventoryOrderKafkaListenerFactory")
    public void handleOrderProductPayment(ReservedInventoryOrderMessage reservedInventoryOrderMessage) {

        // handel idempotency for payment process to prevent duplicate payment process
        Optional<PaymentTransaction> paymentTransactionOptional = paymentTransactionRepository.findById(reservedInventoryOrderMessage.getOrderCreatedMessage().getIdempotentKey());
        PaymentTransaction paymentTransaction;
        if (paymentTransactionOptional.isEmpty()) {
            paymentTransaction= paymentTransactionRepository.save(
                    PaymentTransaction.builder()
                            .id(reservedInventoryOrderMessage.getOrderCreatedMessage().getIdempotentKey())
                            .isOrderPaid(false)
                            .build());
        } else {
            paymentTransaction = paymentTransactionOptional.get();
            // skip if order already get Paid
            if (paymentTransactionOptional.get().getIsOrderPaid())
                return;
        }
        //
        OrderPaymentMessage orderPaymentMessage = OrderPaymentMessage.builder()
                .orderCreatedMessage(reservedInventoryOrderMessage.getOrderCreatedMessage())
                .paymentStatus(PaymentStatus.PENDING)
                .build();
        //
        try {
            // make the payment process
            PaymentStatus paymentStatus = paymentDetailsService.handleOrderPaymentProcess(reservedInventoryOrderMessage.getOrderCreatedMessage());
            orderPaymentMessage.setPaymentStatus(paymentStatus);
            // set payment transaction status true
            paymentTransaction.setIsOrderPaid(true);
            paymentTransactionRepository.save(paymentTransaction);

        } catch (Exception ex) {
            orderPaymentMessage.setPaymentStatus(PaymentStatus.FAILED);
            orderPaymentMessage.setErrorMessage(ex.getMessage());
        }

        messageKafkaTemplate.send(KAFKA_ORDER_PAYMENT_TOPIC_NAME, orderPaymentMessage);
    }


}
