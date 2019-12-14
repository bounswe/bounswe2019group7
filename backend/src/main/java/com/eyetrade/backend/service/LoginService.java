package com.eyetrade.backend.service;

import com.eyetrade.backend.model.entity.User;
import com.eyetrade.backend.model.resource.user.LoginResource;
import com.eyetrade.backend.repository.UserRepository;
import com.eyetrade.backend.model.dto.user.LoginDto;
import com.eyetrade.backend.security.JwtGenerator;
import com.eyetrade.backend.constants.ErrorConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private JwtGenerator jwtGenerator;

    @Autowired
    private UserRepository userRepository;

    public LoginResource login(LoginDto loginDto) throws IllegalAccessException {
        String email = loginDto.getEmail();
        String password = loginDto.getPassword();
        User user = userRepository.findByEmailAndPword(email, password);
        if(user == null){
            throw new IllegalAccessException(ErrorConstants.WRONG_EMAIL_OR_PASSWORD);
        }
        else{
            String token = jwtGenerator.generateToken(user.getId());
            return new LoginResource(token);
        }
    }

}
