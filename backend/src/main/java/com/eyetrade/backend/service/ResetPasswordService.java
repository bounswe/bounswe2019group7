package com.eyetrade.backend.service;

import com.eyetrade.backend.constants.ErrorConstants;
import com.eyetrade.backend.model.entity.User;
import com.eyetrade.backend.repository.UserRepository;
import com.eyetrade.backend.security.JwtResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class ResetPasswordService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @Autowired
    private JwtResolver jwtResolver;

    @Transactional
    public void resetPassword(String password, String confirmationToken) {
        UUID id = jwtResolver.getIdFromToken(confirmationToken);
        User user = userRepository.findById(id);
        if (user == null) {
            throw new RuntimeException(ErrorConstants.USER_NOT_EXIST);
        }
        user.setPword(password);
        user.setConfirmed(true);
        userRepository.save(user);
    }

}
