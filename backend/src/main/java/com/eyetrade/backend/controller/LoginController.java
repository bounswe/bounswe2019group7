package com.eyetrade.backend.controller;

import com.eyetrade.backend.model.dto.LoginDto;
import com.eyetrade.backend.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@Api(value = "Login", tags = {"Operations Related With Login"})
public class LoginController {

    @Autowired
    private LoginService loginService;

    @ApiOperation(value = "Login with the username (email) and password", response = String.class)
    @PostMapping("")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto){
        String token = loginService.login(loginDto);
        return ResponseEntity.ok(token);
    }

}
