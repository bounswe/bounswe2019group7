package com.eyetrade.backend.service;

import com.eyetrade.backend.constants.MessageTypeConstants;
import com.eyetrade.backend.mapper.UserMapper;
import com.eyetrade.backend.model.entity.User;
import com.eyetrade.backend.model.resource.UserResource;
import com.eyetrade.backend.repository.UserRepository;
import com.eyetrade.backend.security.JwtResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResetPasswordService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @Autowired
    private JwtResolver jwtResolver;

    // Todo: Hash password
    public UserResource resetPassword(String password, String confirmationToken) {
        String email = jwtResolver.getUsernameFromToken(confirmationToken);
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException(MessageTypeConstants.USER_NOT_EXIST);
        }
        user.setPword(password);
        user.setConfirmed(true);
        userRepository.save(user);
        return UserMapper.entityToResource(user);
    }

}
