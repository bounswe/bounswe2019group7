package com.traders.backend.security.validators;

import com.traders.backend.security.utilities.JwtResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtBasicValidator {

    @Value("${jwt.secret}") private String secret;

    @Autowired
    private JwtResolver jwtResolver;

    public Boolean validateToken(String token, String email) {
        String username = jwtResolver.getUsernameFromToken(token);
        return (username.equals(email) && !isTokenExpired(token));
    }

    // checks whether the token has expired
    private Boolean isTokenExpired(String token) {
        Date expiration = jwtResolver.getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

}
