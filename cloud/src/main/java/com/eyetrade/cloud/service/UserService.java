package com.eyetrade.cloud.service;

import com.eyetrade.cloud.model.data.entity.Authority;
import com.eyetrade.cloud.model.data.entity.ConfirmationToken;
import com.eyetrade.cloud.model.data.entity.User;
import com.eyetrade.cloud.model.data.repository.AuthorityRepository;
import com.eyetrade.cloud.model.data.repository.ConfirmationTokenRepository;
import com.eyetrade.cloud.model.data.repository.UserRepository;
import com.eyetrade.cloud.model.resource.UserResource;
import com.eyetrade.cloud.util.constants.MessageTypeConstants;
import com.eyetrade.cloud.util.constants.TokenConstants;
import com.eyetrade.cloud.util.constants.UserConstants;
import com.eyetrade.cloud.util.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

import static com.eyetrade.cloud.util.constants.TokenConstants.CONVERT_SECOND_TO_HOUR;

/**
 * Created by Emir Gökdemir
 * on 12 Eki 2019
 */

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private ConfirmationTokenService confirmationTokenService;


    public UserResource save(User user) {
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null) {
            throw new RuntimeException(MessageTypeConstants.MAIL_ALREADY_EXISTS);
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));

        userRepository.save(user);
        authorityRepository.save(new Authority(user.getEmail(), UserConstants.AUTHORITY_USER));
        confirmationTokenService.sendToken(user.getIdentifier(), TokenConstants.USER_REGISTER);

        return UserMapper.toResource(user);
    }


    private long hourDifference(Date date, Date now) {

        return (now.getTime() - date.getTime()) / (CONVERT_SECOND_TO_HOUR);
    }

    public ConfirmationToken confirmToken(String confirmationToken) {
        ConfirmationToken token = confirmationTokenRepository
                .findByConfirmationToken(confirmationToken);
        Calendar cal = Calendar.getInstance();

        if (token == null || token.getTokenStatus().equals(TokenConstants.CONFIRMED)
                || token.getTokenStatus().equals(TokenConstants.EXPIRED)) {
            throw new RuntimeException(MessageTypeConstants.INVALID_TOKEN);
        }

        long hourDifference = hourDifference(token.getCreatedDate(), cal.getTime());

        if (hourDifference > TokenConstants.EXPIRATION_HOUR) {
            token.setTokenStatus(TokenConstants.EXPIRED);
            confirmationTokenRepository.save(token);
            String userId = token.getUser().getIdentifier();
            throw new RuntimeException(
                    MessageTypeConstants.EXPIRED_TOKEN + " UserConstants Id : " + userId);
        }

        token.setTokenStatus(TokenConstants.CONFIRMED);
        confirmationTokenRepository.save(token);

        return token;
    }

    public UserResource confirmRegister(String confirmationToken) {
        ConfirmationToken token = confirmToken(confirmationToken);
        User user = userRepository.findByEmail(token.getUser().getEmail());
        if (user == null) {
            throw new RuntimeException(MessageTypeConstants.USER_NOT_EXIST);
        }
        user.setConfirmed(true);
        userRepository.save(user);
        return UserMapper.toResource(user);
    }

    public UserResource resetPassword(String password, String confirmationToken) {
        ConfirmationToken token = confirmationTokenRepository
                .findByConfirmationToken(confirmationToken);
        if (token == null) {
            throw new RuntimeException(MessageTypeConstants.INVALID_TOKEN);
        }
        User user = userRepository.findByEmail(token.getUser().getEmail());
        if (user == null) {
            throw new RuntimeException(MessageTypeConstants.USER_NOT_EXIST);
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(password));
        user.setConfirmed(true);
        userRepository.save(user);
        return UserMapper.toResource(user);
    }

    public UserResource findUserIdByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException(MessageTypeConstants.USER_NOT_EXIST);
        }
        return UserMapper.toResource(user);
    }

}
