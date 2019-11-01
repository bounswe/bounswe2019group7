package com.eyetrade.backend.service;

import com.eyetrade.backend.model.entity.User;
import com.eyetrade.backend.repository.UserRepository;
import com.eyetrade.backend.model.resource.UserResource;
import com.eyetrade.backend.security.JwtResolver;
import com.eyetrade.backend.constants.ErrorConstants;
import com.eyetrade.backend.constants.Role;
import com.eyetrade.backend.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;


/**
 * Created by Emir Gökdemir
 * on 12 Eki 2019
 */

@Service
public class RegistrationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @Autowired
    private JwtResolver jwtResolver;

    // TODO: 15 Eki 2019 hash password
    @Transactional
    public UserResource save(User user) {
        if(user.getRole().equals(Role.TRADER_USER) && (user.getIban() == null || user.getIdentityNo() == null)) {
            throw new RuntimeException(ErrorConstants.IBAN_AND_IDENTITY_SHOULD_BE_PROVIDED);
        }
        userRepository.saveAndFlush(user);

        //TODO: mail göndermeyi kontrol et
        confirmationTokenService.sendActivationToken(user);

        return UserMapper.entityToResource(user);
    }

    public void confirmRegister(String confirmationToken) {
        UUID id = jwtResolver.getIdFromToken(confirmationToken);
        User user = userRepository.findById(id);
        if (user == null) {
            throw new RuntimeException(ErrorConstants.USER_NOT_EXIST);
        }
        user.setConfirmed(true);
        userRepository.save(user);
    }



}
