package com.eyetrade.backend.controller;

import com.eyetrade.backend.model.dto.UserDto;
import com.eyetrade.backend.model.resource.UserResource;
import com.eyetrade.backend.security.JwtUserChecker;
import com.eyetrade.backend.service.UserProfileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value = "Return current users profile information", response = UserResource.class)
    @GetMapping("/profile")
    public ResponseEntity<UserResource> getSelfProfile(
            @RequestHeader("Authorization") String token
    ){
        UUID userID = jwtUserChecker.resolveBasicToken(token);
        UserResource user = userProfileService.getUserProfile(userID);
        return ResponseEntity.ok(user);
    }

    @ApiOperation(value = "Return profile of given user", response = UserResource.class)
    @GetMapping("/user")
    public ResponseEntity<UserResource> getUserProfile(UUID userID){
        UserResource user = userProfileService.getUserProfile(userID);
        return ResponseEntity.ok(user);
    }

    @ApiOperation(value = "Updates user profile with given info", response = UserResource.class)
    @PostMapping("/updateProfile")
    public ResponseEntity<UserResource> updateUserProfile(
            @RequestHeader("Authorization") String token,
            @RequestBody @Valid UserDto userDto
    ){
        UUID userID = jwtUserChecker.resolveBasicToken(token);
        UserResource user = userProfileService.updateProfile(userID, userDto);
        return ResponseEntity.ok(user);
    }

}
