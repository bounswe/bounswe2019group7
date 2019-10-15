package com.eyetrade.backend.controller;

import com.eyetrade.backend.security.JwtUserChecker;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dummy")
@Api
public class DummyController {

    @Autowired
    private JwtUserChecker jwtUserChecker;

    @GetMapping("/trader")
    public ResponseEntity<String> trader(@RequestHeader("Authentication") String token){
        String email = jwtUserChecker.resolveTraderToken(token);
        return ResponseEntity.ok(email);
    }

    @GetMapping("/basic")
    public ResponseEntity<String> basic(@RequestHeader("Authentication") String token){
        String email = jwtUserChecker.resolveBasicToken(token);
        return ResponseEntity.ok(email);
    }

}
