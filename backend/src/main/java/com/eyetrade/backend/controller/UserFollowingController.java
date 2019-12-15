package com.eyetrade.backend.controller;

import com.eyetrade.backend.model.resource.user.MinimalUserResource;
import com.eyetrade.backend.security.JwtUserChecker;
import com.eyetrade.backend.service.UserFollowingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Api(value = "User Following", tags = {"User Following"})
@RestController
@RequestMapping("/user_following")
public class    UserFollowingController {

    @Autowired
    private UserFollowingService userFollowingService;

    @Autowired
    private JwtUserChecker jwtUserChecker;

    @ApiOperation(value = "A user's request to follow another user", response = MinimalUserResource.class)
    @PostMapping("/follow")
    public ResponseEntity<MinimalUserResource> follow(
            @RequestHeader("Authorization") String token,
            @RequestHeader("followingUserEmail") String followingUserEmail
    ) throws IllegalAccessException{
        UUID userId = jwtUserChecker.resolveBasicToken(token);
        MinimalUserResource followingMinimalUserResource = userFollowingService.followUser(userId, followingUserEmail);
        return ResponseEntity.ok(followingMinimalUserResource);
    }

    @ApiOperation(value = "A user's request to unfollow another user", response = MinimalUserResource.class)
    @DeleteMapping("/unfollow")
    public ResponseEntity<MinimalUserResource> unfollow(
            @RequestHeader("Authorization") String token,
            @RequestHeader("followingUserEmail") String followingUserEmail
    ) throws IllegalAccessException {
        UUID userId = jwtUserChecker.resolveBasicToken(token);
        MinimalUserResource followingMinimalUserResource = userFollowingService.unFollowUser(userId, followingUserEmail);
        return ResponseEntity.ok(followingMinimalUserResource);
    }

    @ApiOperation(
            value = "Get a user's followers. If an email is given we look for email owner's followers, " +
                    "otherwise we look for the request sender's followers",
            response = MinimalUserResource.class,
            responseContainer = "List"
    )
    @GetMapping("/get_followers")
    public ResponseEntity<List<MinimalUserResource>> getFollowers(
            @RequestHeader("Authorization") String token,
            @RequestHeader(value = "otherUserEmail", required = false) String otherUserEmail
    )  throws IllegalAccessException {
        UUID userId = jwtUserChecker.resolveBasicToken(token);
        List<MinimalUserResource> followers;
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
            response = MinimalUserResource.class,
            responseContainer = "List"
    )
    @GetMapping("/get_followings")
    public ResponseEntity<List<MinimalUserResource>> getFollowings(
            @RequestHeader("Authorization") String token,
            @RequestHeader(value = "otherUserEmail", required = false) String otherUserEmail
    ) throws IllegalAccessException {
        UUID userId = jwtUserChecker.resolveBasicToken(token);
        List<MinimalUserResource> followings;
        if(otherUserEmail == null){
            followings = userFollowingService.getSelfFollowings(userId);
        }
        else{
            followings = userFollowingService.getUserFollowings(otherUserEmail);
        }
        return ResponseEntity.ok(followings);
    }

    // TODO: 8 Ara 2019 unfollow

}
