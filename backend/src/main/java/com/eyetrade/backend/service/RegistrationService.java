package com.eyetrade.backend.service;

import com.eyetrade.backend.mapper.PredictionMapper;
import com.eyetrade.backend.model.dto.user.BasicUserDto;
import com.eyetrade.backend.model.dto.user.TraderUserDto;
import com.eyetrade.backend.model.entity.PredictionCountOfUser;
import com.eyetrade.backend.model.entity.User;
import com.eyetrade.backend.model.resource.user.CompleteUserResource;
import com.eyetrade.backend.repository.PredictionCountRepository;
import com.eyetrade.backend.repository.UserRepository;
import com.eyetrade.backend.security.JwtResolver;
import com.eyetrade.backend.constants.ErrorConstants;
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
    private PredictionCountRepository predictionCountRepository;

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @Autowired
    private JwtResolver jwtResolver;

    @Transactional
    public CompleteUserResource saveBasicUser(BasicUserDto basicUserDto, String password) {
        User user = UserMapper.basicUserDtoToEntity(basicUserDto);
        user.setPword(password);
        return save(user);
    }

    @Transactional
    public CompleteUserResource saveTraderUser(TraderUserDto traderRegisterDto, String password) {
        User user = UserMapper.traderUserDtoToEntity(traderRegisterDto);
        user.setPword(password);
        return save(user);
    }

    @Transactional
    public CompleteUserResource save(User user) {
        userRepository.saveAndFlush(user);
        PredictionCountOfUser predictionCountOfUser = PredictionMapper.createPredictionCount(user.getId());
        predictionCountRepository.saveAndFlush(predictionCountOfUser);
        // TODO: Sending email generates errors for now, so I commented it
        //confirmationTokenService.sendActivationToken(user);
        // for a new register user follower and followings are 0
        return UserMapper.entityToCompleteUserResource(user, 0, 0, predictionCountOfUser);
    }

    @Transactional
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
