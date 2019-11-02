package com.eyetrade.backend.service;

import com.eyetrade.backend.model.entity.User;
import com.eyetrade.backend.repository.UserRepository;
import com.eyetrade.backend.security.JwtGenerator;
import com.eyetrade.backend.constants.ErrorConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import static com.eyetrade.backend.constants.MailConstants.*;

/**
 * Created by Emir Gökdemir
 * on 12 Eki 2019
 */

@Service
public class ConfirmationTokenService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Environment environment;

    @Autowired
    private JwtGenerator jwtGenerator;

    @Autowired
    private UserRepository userRepository;


    public void sendActivationToken(User user) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        String confirmationToken = jwtGenerator.generateToken(user.getId());
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject(CONFIRM_ACCOUNT_HEADER);
        mailMessage.setText(CONFIRM_ACCOUNT_BODY
                + this.environment.getProperty("spring.url")
                + CONFIRM_ACCOUNT_URL + confirmationToken);
        sendMail(mailMessage);
    }

    public void sendResetPasswordsToken(String email) {
        User user = userRepository.findByEmail(email);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        String confirmationToken = jwtGenerator.generateToken(user.getId());
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject(RESET_PASSWORD_HEADER);
        mailMessage.setText(RESET_PASSWORD_BODY
                + this.environment.getProperty("spring.url")
                + RESET_PASSWORD_URL + confirmationToken);
        sendMail(mailMessage);
    }

    private void sendMail(SimpleMailMessage mailMessage){
        try {
            javaMailSender.send(mailMessage);
        } catch (MailException exception) {
            throw new RuntimeException(ErrorConstants.MAIL_SEND_FAILED);
        }
    }
}

