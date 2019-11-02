package com.eyetrade.backend.controller;

import com.eyetrade.backend.security.JwtUserChecker;
import com.eyetrade.backend.service.UserProfileService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Api(value = "User Profile", tags = {"User Profile operations"})
@RestController
@RequestMapping("/user_profile")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private JwtUserChecker jwtUserChecker;
}
