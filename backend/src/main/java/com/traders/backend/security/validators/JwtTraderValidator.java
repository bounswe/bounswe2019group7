package com.traders.backend.security.validators;

import com.traders.backend.models.domain_models.User;
import com.traders.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JwtTraderValidator {

    @Autowired
    private JwtBasicValidator jwtBasicValidator;


    @Autowired
    private UserRepository userRepository;

    public Boolean validate(String token, String email){
        if(isTradingUser(email)){
            return jwtBasicValidator.validateToken(token, email);
        }
        else{ // is not a trading user
            return false;
        }
    }

    private Boolean isTradingUser(String email){
        List<User> users = userRepository.findByEmail(email);
        return !users.isEmpty();
    }

}
