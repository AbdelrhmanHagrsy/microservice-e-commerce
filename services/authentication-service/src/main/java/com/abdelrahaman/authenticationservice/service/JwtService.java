package com.abdelrahaman.authenticationservice.service;

import com.abdelrahaman.authenticationservice.entity.User;
import com.abdelrahaman.authenticationservice.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.util.*;

@Component
public class JwtService {

    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
    @Autowired
    private UserRepository userRepository;

    public void isTokenValid(String token){
        Jwts.parser().setSigningKey(getSignKey()).build().parseClaimsJws(token);
    }

    public String GenerateToken(String userName){
        Map<String,Object> claims = new HashMap<>();
        String role = getUserRoles(userName);
        claims.put("roles",role);
        return Jwts.builder()
                .claims(claims)
                .subject(userName)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }


    private String getUserRoles(String userName){
        User user = userRepository.findByUserName(userName).get();
        return user.getUserRoles().name();

    }
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
