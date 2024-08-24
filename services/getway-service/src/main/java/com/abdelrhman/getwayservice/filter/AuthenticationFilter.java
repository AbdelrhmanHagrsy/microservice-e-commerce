package com.abdelrhman.getwayservice.filter;

import com.abdelrhman.getwayservice.client.AuthClient;
import com.abdelrhman.getwayservice.util.JwtUtil;
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
                    /*boolean isValid = authClient.validateToken(token);
                    if(!isValid)
                        throw new RuntimeException("token is unValid!");
                     */

                    jwtUtil.isTokenValid(token);

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
