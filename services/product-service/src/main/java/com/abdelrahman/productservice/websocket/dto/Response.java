package com.abdelrahman.productservice.websocket.dto;

import com.abdelrahman.productservice.dto.OfferDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Response {

    List<OfferDto> offerDtoList;
}
