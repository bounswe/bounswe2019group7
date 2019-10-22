package com.eyetrade.backend.controller;

import com.eyetrade.backend.model.dto.LoginDto;
import com.eyetrade.backend.service.LoginService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@Api(value = "Login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto){
        String token = loginService.login(loginDto);
        return ResponseEntity.ok(token);
    }

}
