package com.eyetrade.backend.controller;

import com.eyetrade.backend.security.JwtUserChecker;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Api(value = "Dummy", tags = {"Dummy Operations, Will be Removed in the Future"})
@RestController
@RequestMapping("/dummy")
public class DummyController {

    @Autowired
    private JwtUserChecker jwtUserChecker;

    @ApiOperation(value = "Test the authorization of trader user", response = String.class)
    @GetMapping("/trader")
    public ResponseEntity<?> trader(@RequestHeader("Authorization") String token){
        UUID id = jwtUserChecker.resolveTraderToken(token);
        return ResponseEntity.ok(id);
    }

    @ApiOperation(value = "Test the authorization of basic user", response = String.class)
    @GetMapping("/basic")
    public ResponseEntity<?> basic(@RequestHeader("Authorization") String token){
        UUID id = jwtUserChecker.resolveBasicToken(token);
        return ResponseEntity.ok(id);
    }

}
