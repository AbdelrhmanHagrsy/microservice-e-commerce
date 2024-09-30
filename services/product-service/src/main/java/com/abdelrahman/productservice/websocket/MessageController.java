package com.abdelrahman.productservice.websocket;

import com.abdelrahman.productservice.dto.OfferDto;
import com.abdelrahman.productservice.service.OfferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MessageController {

    private final SimpMessagingTemplate messagingTemplate;
    private final OfferService offerService;


    @Scheduled(fixedRate = 3600000) // Send message every hour (3600000 ms)
    public void sendOffers(){
        log.info("Websocket start fetching offers and send to topic offer");
        List<OfferDto> offerDtoList =  offerService.getAllActiveOffer();
        messagingTemplate.convertAndSend("/topic/offer",offerDtoList);
    }
}
