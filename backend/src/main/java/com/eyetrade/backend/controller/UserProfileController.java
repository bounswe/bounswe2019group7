package com.eyetrade.backend.controller;

import com.eyetrade.backend.mapper.UserMapper;
import com.eyetrade.backend.model.dto.UserDto;
import com.eyetrade.backend.model.entity.User;
import com.eyetrade.backend.security.JwtUserChecker;
import com.eyetrade.backend.service.UserProfileService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;


@Api(value = "User Profile", tags = {"User Profile operations"})
@RestController
@RequestMapping("/user_profile")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private JwtUserChecker jwtUserChecker;

    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfile(
            @RequestHeader("Authorization") String token
    ){
        UUID userID = jwtUserChecker.resolveBasicToken(token);
        User user = userProfileService.getUserProfile(userID);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/updateProfile")
    public ResponseEntity<User> updateUserProfile(
            @RequestHeader("Authorization") String token,
            @RequestBody @Valid UserDto userDto
    ){
        UUID userID = jwtUserChecker.resolveBasicToken(token);
        User user = userProfileService.updateProfile(userID, UserMapper.dtoToEntity(userDto));
        return ResponseEntity.ok(user);
    }

}
