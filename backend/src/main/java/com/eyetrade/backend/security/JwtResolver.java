package com.eyetrade.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;
import java.util.function.Function;

import static com.eyetrade.backend.constants.ErrorConstants.INVALID_TOKEN;

@Component
public class JwtResolver {

    @Value("${jwt.secret}") private String secret;

    // Extracts the username(email) from the given token
    public UUID getIdFromToken(String token) {
        String idString;
        try{
         idString = getClaimFromToken(token, Claims::getSubject);
        } catch (Exception e){
            throw new RuntimeException(INVALID_TOKEN);
        }
        return UUID.fromString(idString);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }


}
