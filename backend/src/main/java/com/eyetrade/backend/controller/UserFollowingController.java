package com.eyetrade.backend.controller;

import com.eyetrade.backend.model.resource.UserResource;
import com.eyetrade.backend.security.JwtUserChecker;
import com.eyetrade.backend.service.UserFollowingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Api(value = "User Following", tags = {"Operations Related with User Following Relations"})
@RestController
@RequestMapping("/user_following")
public class    UserFollowingController {

    @Autowired
    private UserFollowingService userFollowingService;

    @Autowired
    private JwtUserChecker jwtUserChecker;

    @ApiOperation(value = "A user's request to follow another user", response = UserResource.class)
    @PostMapping("/follow")
    public ResponseEntity<UserResource> follow(
            @RequestHeader("Authorization") String token,
            @RequestHeader("followingUserEmail") String followingUserEmail
    ) throws IllegalAccessException{
        UUID userId = jwtUserChecker.resolveBasicToken(token);
        UserResource followingUserResource = userFollowingService.followUser(userId, followingUserEmail);
        return ResponseEntity.ok(followingUserResource);
    }

    @ApiOperation(
            value = "Get a user's followers. If an email is given we look for email owner's followers, " +
                    "otherwise we look for the request sender's followers",
            response = UserResource.class,
            responseContainer = "List"
    )
    @GetMapping("/getFollowers")
    public ResponseEntity<List<UserResource>> getFollowers(
            @RequestHeader("Authorization") String token,
            @RequestHeader(value = "otherUserEmail", required = false) String otherUserEmail
    )  throws IllegalAccessException {
        UUID userId = jwtUserChecker.resolveBasicToken(token);
        List<UserResource> followers;
        if(otherUserEmail == null){
            followers = userFollowingService.getSelfFollowers(userId);
        }
        else{
            followers = userFollowingService.getUserFollowers(otherUserEmail);
        }
        return ResponseEntity.ok(followers);
    }


    @ApiOperation(
            value = "Get a user's followings. If an email is given we look for email owner's followings, " +
                    "otherwise we look for the request sender's followings",
            response = UserResource.class,
            responseContainer = "List"
    )
    @GetMapping("/getFollowings")
    public ResponseEntity<List<UserResource>> getFollowings(
            @RequestHeader("Authorization") String token,
            @RequestHeader(value = "otherUserEmail", required = false) String otherUserEmail
    ) throws IllegalAccessException {
        UUID userId = jwtUserChecker.resolveBasicToken(token);
        List<UserResource> followings;
        if(otherUserEmail == null){
            followings = userFollowingService.getSelfFollowings(userId);
        }
        else{
            followings = userFollowingService.getUserFollowings(otherUserEmail);
        }
        return ResponseEntity.ok(followings);
    }

}
