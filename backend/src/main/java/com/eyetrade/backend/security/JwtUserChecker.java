package com.eyetrade.backend.security;

import com.eyetrade.backend.constants.ErrorConstants;
import com.eyetrade.backend.constants.Role;
import com.eyetrade.backend.model.entity.User;
import com.eyetrade.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

//TODO: refactor the code
@Component
public class JwtUserChecker {

    @Value("${jwt.secret}") private String secret;

    @Autowired
    private JwtResolver jwtResolver;

    @Autowired
    private UserRepository userRepository;

    public UUID resolveTraderToken(String token) throws IllegalAccessException {
        UUID id = resolveBasicToken(token);
        User user = userRepository.findById(id);
        if(user.getRole() == Role.BASIC_USER){
            throw new RuntimeException(ErrorConstants.NOT_TRADER_USER);
        }
        return id;
    }

    public UUID resolveBasicToken(String token) throws IllegalAccessException {
        if(isTokenExpired(token)){
            throw new RuntimeException(ErrorConstants.EXPIRED_TOKEN);
        }
        UUID id=jwtResolver.getIdFromToken(token);
        if(userRepository.findById(id)==null){
            throw new IllegalAccessException(ErrorConstants.USER_NOT_EXIST);
        }
        return id;
    }

    // checks whether the token has expired
    private Boolean isTokenExpired(String token) {
        Date expiration = jwtResolver.getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

}
