package com.eyetrade.backend.controller;

import com.eyetrade.backend.constants.PrivacyType;
import com.eyetrade.backend.model.dto.user.BasicToTraderDto;
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


@Api(value = "User Profile", tags = {"User Profile"})
@RestController
@RequestMapping("/user_profile")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private JwtUserChecker jwtUserChecker;

    @ApiOperation(value = "Return current users profile information", response = CompleteUserResource.class)
    @GetMapping("/self_profile")
    public ResponseEntity getSelfProfile(
            @RequestHeader("Authorization") String token
    )  {
        try {
            UUID userID = jwtUserChecker.resolveBasicToken(token);
            CompleteUserResource user = userProfileService.getSelfProfile(userID);
            return ResponseEntity.ok(user);
        } catch (IllegalAccessException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation(value = "Return profile of a given user. If the user is private and you don't follow it then returns null",
            response = CompleteUserResource.class)
    @GetMapping("/other_profile")
    public ResponseEntity getOtherProfile(
            @RequestHeader("Authorization") String token,
            @RequestHeader("email") String email
    ){
        try {
            UUID userID = jwtUserChecker.resolveBasicToken(token);
            PartialUserResource user = userProfileService.getOtherProfile(userID, email);
            return ResponseEntity.ok(user);
        } catch (IllegalAccessException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation(value = "Updates a basic profile with the given info", response = CompleteUserResource.class)
    @PostMapping("/update_basic_profile")
    public ResponseEntity updateBasicUserProfile(
            @RequestHeader("Authorization") String token,
            @RequestBody @Valid BasicUserDto basicUserDto
    ){
        try {
            UUID userID = jwtUserChecker.resolveBasicToken(token);
            CompleteUserResource user = userProfileService.updateBasicProfile(userID, basicUserDto);
            return ResponseEntity.ok(user);
        } catch (IllegalAccessException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation(value = "Updates a trader profile with the given info", response = CompleteUserResource.class)
    @PostMapping("/update_trader_profile")
    public ResponseEntity updateTraderUserProfile(
            @RequestHeader("Authorization") String token,
            @RequestBody @Valid TraderUserDto traderUserDto
    ){
        try {
            UUID userID = jwtUserChecker.resolveTraderToken(token);
            CompleteUserResource user = userProfileService.updateTraderProfile(userID, traderUserDto);
            return ResponseEntity.ok(user);
        } catch (IllegalAccessException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation(value = "Updates a user's profile privacy", response = CompleteUserResource.class)
    @PostMapping("/update_privacy")
    public ResponseEntity updateUserPrivacy(
            @RequestHeader("Authorization") String token,
            @RequestHeader("privacy") PrivacyType privacy
    ){
        try {
            UUID userID = jwtUserChecker.resolveBasicToken(token);
            CompleteUserResource user = userProfileService.updatePrivacy(userID, privacy);
            return ResponseEntity.ok(user);
        } catch (IllegalAccessException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation(value = "Upgrades a basic user to trader user", response = CompleteUserResource.class)
    @PostMapping("/upgrade_basic_to_trader")
    public ResponseEntity upgradeBasicToTrader(
            @RequestHeader("Authorization") String token,
            @RequestBody @Valid BasicToTraderDto basicToTraderDto
    ){
        try {
            UUID userId = jwtUserChecker.resolveBasicToken(token);
            CompleteUserResource user = userProfileService.upgradeBasicToTrader(userId, basicToTraderDto);
            return ResponseEntity.ok(user);
        } catch (IllegalAccessException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
