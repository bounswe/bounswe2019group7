package com.eyetrade.cloud.service;

import com.eyetrade.cloud.model.data.entity.ConfirmationToken;
import com.eyetrade.cloud.model.data.entity.User;
import com.eyetrade.cloud.model.data.repository.ConfirmationTokenRepository;
import com.eyetrade.cloud.model.data.repository.UserRepository;
import com.eyetrade.cloud.util.constants.MessageTypeConstants;

import com.eyetrade.cloud.util.constants.TokenConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import static com.eyetrade.cloud.util.constants.MailConstants.*;

/**
 * Created by Emir Gökdemir
 * on 12 Eki 2019
 */

@Service
public class ConfirmationTokenService {

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    private Environment environment;

    private ConfirmationTokenService(Environment environment) {
        this.environment = environment;
    }

    public String sendToken(String email, String type,User user) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        ConfirmationToken confirmationToken=new ConfirmationToken();
        if (type.equals(TokenConstants.USER_REGISTER)) {
            confirmationToken = new ConfirmationToken(user, type);
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject(CONFIRM_ACCOUNT_HEADER);
            mailMessage.setText(CONFIRM_ACCOUNT_BODY
                    + this.environment.getProperty("spring.url")
                    + CONFIRM_ACCOUNT_URL + confirmationToken.getConfirmationToken());
        } else if (type.equals(TokenConstants.PASSWORD_RESET)) {
            user = userRepository.findByEmail(email);
            if (user == null) {
                throw new RuntimeException(MessageTypeConstants.USER_NOT_EXIST);
            }
            confirmationToken = new ConfirmationToken(user, type);
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject(RESET_PASSWORD_HEADER);
            mailMessage.setText(RESET_PASSWORD_BODY
                    + this.environment.getProperty("spring.url")
                    + RESET_PASSWORD_URL + confirmationToken.getConfirmationToken());
        }
        try {
            javaMailSender.send(mailMessage);
        } catch (RuntimeException exception) {
            throw new RuntimeException(MessageTypeConstants.MAIL_SEND_FAILED);
        }
        confirmationTokenRepository.save(confirmationToken);

        return confirmationToken.getConfirmationToken();
    }
}

