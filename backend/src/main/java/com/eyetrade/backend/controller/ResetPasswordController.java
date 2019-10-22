package com.eyetrade.backend.controller;

import com.eyetrade.backend.model.resource.UserResource;
import com.eyetrade.backend.service.ConfirmationTokenService;
import com.eyetrade.backend.service.ResetPasswordService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Emir Gökdemir
 * on 12 Eki 2019
 */

@RestController
@RequestMapping(value = {"/confirmation"})
@Api(value = "Reset Password")
public class ResetPasswordController {

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @Autowired
    private ResetPasswordService resetPasswordService;

    @GetMapping("/send-token")
    public ResponseEntity<String> resetPassword(@RequestParam("email") String email){
        confirmationTokenService.sendResetPasswordsToken(email);
        return ResponseEntity.ok("Success");
    }

    @GetMapping("/reset-password")
    public UserResource resetPassword(@RequestParam("password") String password,
                                      @RequestParam("token") String confirmationToken) {
        return resetPasswordService.resetPassword(password, confirmationToken);
    }


}