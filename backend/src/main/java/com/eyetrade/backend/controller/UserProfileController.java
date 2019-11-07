package com.eyetrade.backend.controller;

import com.eyetrade.backend.model.dto.user.BasicUserDto;
import com.eyetrade.backend.model.dto.user.TraderUserDto;
import com.eyetrade.backend.model.resource.user.CompleteUserResource;
import com.eyetrade.backend.model.resource.user.PartialUserResource;
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

    @ApiOperation(value = "Return current users profile information", response = CompleteUserResource.class)
    @GetMapping("/profile")
    public ResponseEntity<CompleteUserResource> getSelfProfile(
            @RequestHeader("Authorization") String token
    ){
        UUID userID = jwtUserChecker.resolveBasicToken(token);
        CompleteUserResource user = userProfileService.getSelfProfile(userID);
        return ResponseEntity.ok(user);
    }

    @ApiOperation(value = "Return profile of a given user. If the user is private and you don't follow it then returns null",
            response = CompleteUserResource.class)
    @GetMapping("/user")
    public ResponseEntity<PartialUserResource> getOtherProfile(
            @RequestHeader("Authorization") String token,
            @RequestHeader("email") String email
    ){
        UUID userID = jwtUserChecker.resolveBasicToken(token);
        PartialUserResource user = userProfileService.getOtherProfile(userID, email);
        return ResponseEntity.ok(user);
    }

    @ApiOperation(value = "Updates a basic profile with the given info", response = CompleteUserResource.class)
    @PostMapping("/updateBasicProfile")
    public ResponseEntity<CompleteUserResource> updateBasicUserProfile(
            @RequestHeader("Authorization") String token,
            @RequestBody @Valid BasicUserDto basicUserDto
    ){
        UUID userID = jwtUserChecker.resolveBasicToken(token);
        CompleteUserResource user = userProfileService.updateBasicProfile(userID, basicUserDto);
        return ResponseEntity.ok(user);
    }

    @ApiOperation(value = "Updates a trader profile with the given info", response = CompleteUserResource.class)
    @PostMapping("/updateTraderProfile")
    public ResponseEntity<CompleteUserResource> updateTraderUserProfile(
            @RequestHeader("Authorization") String token,
            @RequestBody @Valid TraderUserDto traderUserDto
    ){
        UUID userID = jwtUserChecker.resolveBasicToken(token);
        CompleteUserResource user = userProfileService.updateTraderProfile(userID, traderUserDto);
        return ResponseEntity.ok(user);
    }

}
