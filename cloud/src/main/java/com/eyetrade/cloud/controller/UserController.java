package com.eyetrade.cloud.controller;

import com.eyetrade.cloud.model.dto.UserDto;
import com.eyetrade.cloud.model.resource.UserResource;
import com.eyetrade.cloud.service.UserService;
import com.eyetrade.cloud.util.mapper.UserMapper;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/** Created by Emir Gökdemir
 on 12 Eki 2019 */

@RestController
@RequestMapping(value = {"/user"})
@Api
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public List<UserResource> allUsers(){
        return userService.all();
    }

    @PostMapping("/register")
    public UserResource registerUser(@RequestBody @Valid UserDto userDto) {
        return userService.save(UserMapper.dtoToEntity(userDto));
    }

    @GetMapping("/confirm-register")
    public UserResource confirmRegister(@RequestParam("token") String confirmationToken) {
        return userService.confirmRegister(confirmationToken);
    }

    @GetMapping("/reset-password")
    public UserResource resetPassword(@RequestParam("password") String password,
                                      @RequestParam("token") String confirmationToken) {
        return userService.resetPassword(password, confirmationToken);
    }

    @GetMapping("/{email}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public UserResource findUserIdByEmail(@PathVariable("email") String email) {
        return userService.findUserIdByEmail(email);
    }

    @GetMapping("/basic")
    public String basicUserWelcomePage(){
        return "Welcome Basic User!";
    }

    @GetMapping("/trader")
    public String traderUserWelcomePage(){
        return "Welcome Trader User!";
    }
}