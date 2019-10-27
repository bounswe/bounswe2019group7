package com.eyetrade.backend.controller;

import com.eyetrade.backend.model.resource.UserResource;
import com.eyetrade.backend.service.ConfirmationTokenService;
import com.eyetrade.backend.service.ResetPasswordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "Reset Password", tags = {"Operations Related With Password Reset"})
public class ResetPasswordController {

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @Autowired
    private ResetPasswordService resetPasswordService;

    @ApiOperation(value = "Send a reset token to the email of the user", response = String.class)
    @GetMapping("/send-token")
    public ResponseEntity<String> resetPassword(@RequestParam("email") String email){
        confirmationTokenService.sendResetPasswordsToken(email);
        return ResponseEntity.ok("Success");
    }

    @ApiOperation(value = "Reset the user's password by using the link in the confirmation mail", response = UserResource.class)
    @GetMapping("/reset-password")
    public ResponseEntity<UserResource> resetPassword(@RequestParam("password") String password,
                                      @RequestParam("token") String confirmationToken) {
        UserResource user = resetPasswordService.resetPassword(password, confirmationToken);
        return ResponseEntity.ok(user);
    }


}