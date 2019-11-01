package com.eyetrade.backend.controller;

import com.eyetrade.backend.model.resource.UserResource;
import com.eyetrade.backend.security.JwtUserChecker;
import com.eyetrade.backend.service.UserFollowingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Api(value = "User Following", tags = {"Operations Related With User following relations"})
@RestController
@RequestMapping("/user_following")
public class UserFollowingController {

    @Autowired
    private UserFollowingService userFollowingService;

    @Autowired
    private JwtUserChecker jwtUserChecker;

    @ApiOperation(value = "A user sends a request to follow another user", response = UserResource.class)
    @GetMapping("/follow")
    public ResponseEntity<UserResource> follow(String token, String followingUserEmail){
        UUID userId = jwtUserChecker.resolveBasicToken(token);
        UserResource followingUserResource = userFollowingService.followUser(userId, followingUserEmail);
        return ResponseEntity.ok(followingUserResource);
    }

}
