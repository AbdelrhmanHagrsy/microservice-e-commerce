package com.abdelrhman.getwayservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.abdelrhman.getwayservice.constant.Constant.AUTHENTICATION_SERVICE;
import static com.abdelrhman.getwayservice.constant.Constant.VALIDATE_TOKEN;


@FeignClient(AUTHENTICATION_SERVICE)
public interface AuthClient {
    @GetMapping(VALIDATE_TOKEN)
    Boolean validateToken(@RequestParam(name = "token") String JwtToken);
}



