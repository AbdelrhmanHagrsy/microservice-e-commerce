package com.abdelrhman.getwayservice.filter;

import com.abdelrhman.getwayservice.client.AuthClient;
import com.abdelrhman.getwayservice.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    @Lazy
    private AuthClient authClient;

    @Autowired
    private RouteValidator routeValidator;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {

        return (((exchange, chain) -> {
            if(routeValidator.isScured.test(exchange.getRequest())){
                if(! exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION))
                    throw new RuntimeException("missing authorization header");
                String token = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if(token != null && token.startsWith("Bearer ")){
                    token = token.substring(7);
                }
                try {
                    // call validateToken api from auth-service by feignClient
                    jwtUtil.isTokenValid(token);
                    // set user role in request header
                    Claims claims = jwtUtil.getAllClaimsFromToken(token);
                    String role = claims.get("roles",String.class);
                    exchange.getRequest().mutate().header("X-User-Roles",role);

                } catch (Exception e) {
                    System.out.println("invalid access...!");
                    throw new RuntimeException("un authorized access to application");
                }

            }
            return chain.filter(exchange);
        }));
    }


    public static class Config {

    }
}
