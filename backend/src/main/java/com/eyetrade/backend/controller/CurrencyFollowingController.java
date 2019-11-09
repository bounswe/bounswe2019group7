package com.eyetrade.backend.controller;

import com.eyetrade.backend.constants.CurrencyType;
import com.eyetrade.backend.model.resource.CurrencyFollowingResource;
import com.eyetrade.backend.model.resource.UserResource;
import com.eyetrade.backend.security.JwtUserChecker;
import com.eyetrade.backend.service.CurrencyFollowingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


/**
 * Created by Emir Gökdemir
 * on 2 Kas 2019
 */
@Api(value = "Currency Following", tags = {"Operations Related with Currency Following Relations"})
@RestController
@RequestMapping("/currency_following")
public class CurrencyFollowingController {


    @Autowired
    private CurrencyFollowingService currencyFollowingService;

    @Autowired
    private JwtUserChecker jwtUserChecker;

    @ApiOperation(value = "A user's request to follow currencies with a base currency type.", response = CurrencyFollowingResource.class)
    @PostMapping("/follow")
    public ResponseEntity<CurrencyFollowingResource> follow(
            @RequestHeader("Authorization") String token,
            @RequestHeader("BaseCurrencyType")CurrencyType baseCurrencyType
            )  throws IllegalAccessException{
        UUID userId = jwtUserChecker.resolveBasicToken(token);
        return ResponseEntity.ok(currencyFollowingService.followCurrency(userId, baseCurrencyType));
    }


    @ApiOperation(
            value = "Get a user's currency followings.",
            response = UserResource.class,
            responseContainer = "List"
    )
    @GetMapping("/getFollowers")
    public ResponseEntity<CurrencyFollowingResource> getFollowers(
            @RequestHeader("Authorization") String token) throws IllegalAccessException {
        UUID userId = jwtUserChecker.resolveBasicToken(token);
        return ResponseEntity.ok(currencyFollowingService.getFollowings(userId));
    }
}
