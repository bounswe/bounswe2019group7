package com.eyetrade.backend.security;

import com.eyetrade.backend.constants.MessageTypeConstants;
import com.eyetrade.backend.constants.Role;
import com.eyetrade.backend.model.entity.User;
import com.eyetrade.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

//TODO: refactor the code
@Component
public class JwtUserChecker {

    @Value("${jwt.secret}") private String secret;

    @Autowired
    private JwtResolver jwtResolver;

    @Autowired
    private UserRepository userRepository;

    public String resolveTraderToken(String token){
        String email = resolveBasicToken(token);
        User user = userRepository.findByEmail(email);
        if(user.getRole() == Role.BASIC_USER){
            throw new RuntimeException(MessageTypeConstants.NOT_TRADER_USER);
        }
        return email;
    }

    public String resolveBasicToken(String token) {
        String email = jwtResolver.getUsernameFromToken(token);
        if(isTokenExpired(token)){
            throw new RuntimeException(MessageTypeConstants.EXPIRED_TOKEN);
        }
        return email;
    }

    // checks whether the token has expired
    private Boolean isTokenExpired(String token) {
        Date expiration = jwtResolver.getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

}
