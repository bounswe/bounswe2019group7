package com.eyetrade.backend.service;

import com.eyetrade.backend.model.entity.User;
import com.eyetrade.backend.repository.UserRepository;
import com.eyetrade.backend.model.dto.LoginDto;
import com.eyetrade.backend.security.JwtGenerator;
import com.eyetrade.backend.constants.ErrorConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    JwtGenerator jwtGenerator;

    @Autowired
    UserRepository userRepository;

    public String login(LoginDto loginDto){
        String email = loginDto.getEmail();
        String password = loginDto.getPassword();
        User user = userRepository.findByEmailAndPword(email, password);
        if(user == null){
            throw new RuntimeException(ErrorConstants.WRONG_EMAIL_OR_PASSWORD);
        }
        else{
            return jwtGenerator.generateToken(email);
        }
    }

}
