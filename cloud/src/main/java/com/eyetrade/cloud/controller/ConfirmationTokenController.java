package com.eyetrade.cloud.controller;

import com.eyetrade.cloud.service.ConfirmationTokenService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
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
@Api
public class ConfirmationTokenController {

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @GetMapping("/send-token")
    public String sendToken(@RequestParam("email") String email, @RequestParam("type") String type){
        return confirmationTokenService.sendToken(email, type,null);
    }


}