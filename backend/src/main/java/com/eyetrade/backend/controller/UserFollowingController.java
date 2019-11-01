package com.eyetrade.backend.controller;

import com.eyetrade.backend.model.resource.UserResource;
import com.eyetrade.backend.security.JwtUserChecker;
import com.eyetrade.backend.service.UserFollowingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Api(value = "User Following", tags = {"Operations Related With User following relations"})
@RestController
@RequestMapping("/user_following")
public class UserFollowingController {

    @Autowired
    private UserFollowingService userFollowingService;

    @Autowired
    private JwtUserChecker jwtUserChecker;

    @ApiOperation(value = "A user's request to follow another user", response = UserResource.class)
    @GetMapping("/follow")
    public ResponseEntity<UserResource> follow(
            @RequestHeader("Authorization") String token,
            @RequestHeader("followingUserEmail") String followingUserEmail
    ) {
        UUID userId = jwtUserChecker.resolveBasicToken(token);
        UserResource followingUserResource = userFollowingService.followUser(userId, followingUserEmail);
        return ResponseEntity.ok(followingUserResource);
    }


    @ApiOperation(
            value = "Get a user's followers. If an email is given we look for email owner's followers, " +
                    "otherwise we look for the request sender's followers",
            response = List.class
    )
    @GetMapping("/getFollowers")
    public ResponseEntity<List<UserResource>> getFollowers(
            @RequestHeader("Authorization") String token,
            @RequestHeader(value = "otherUserEmail", required = false) String otherUserEmail
    ) {
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
            response = List.class
    )
    @GetMapping("/getFollowings")
    public ResponseEntity<List<UserResource>> getFollowings(
            @RequestHeader("Authorization") String token,
            @RequestHeader(value = "otherUserEmail", required = false) String otherUserEmail
    ) {
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
