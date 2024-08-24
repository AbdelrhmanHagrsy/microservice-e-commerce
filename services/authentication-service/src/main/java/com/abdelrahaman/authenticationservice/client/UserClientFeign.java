package com.abdelrahaman.authenticationservice.client;

import com.abdelrahaman.authenticationservice.dto.UserAuthDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static com.abdelrahaman.authenticationservice.constant.Constant.Client.GET_USER_BY_USER_NAME;
import static com.abdelrahaman.authenticationservice.constant.Constant.Client.USER_SERVICE_NAME;

@FeignClient(name = USER_SERVICE_NAME)
public interface UserClientFeign {

    @GetMapping(GET_USER_BY_USER_NAME)
    public UserAuthDto getUserByUserName(@PathVariable("username") String userName);
}
