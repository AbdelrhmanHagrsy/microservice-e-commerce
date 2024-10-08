package com.abdelrhman.getwayservice.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    private final static List<String> openApiEndpoints =List.of(
            "/auth/user/register",
            "/auth/user/login",
            "/auth/user/confirm-account",
            "/auth/user/validateToken",
            "/auth/user/test",
            "/auth/user/confirmAccount",
            "/auth/user/changeRole"
    );

   public Predicate<ServerHttpRequest> isScured =
           serverHttpRequest ->
       openApiEndpoints.stream().noneMatch(uri-> serverHttpRequest.getURI().getPath().contains(uri));

}
