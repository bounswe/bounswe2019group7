package com.eyetrade.cloud.controller;

import com.eyetrade.cloud.model.dto.UserDto;
import com.eyetrade.cloud.model.resource.UserResource;
import com.eyetrade.cloud.service.UserService;
import com.eyetrade.cloud.util.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;

/** Created by Emir Gökdemir
 on 12 Eki 2019 */

@RestController
@RequestMapping(value = {"/user"})
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public UserResource registerUser(@RequestBody @Valid UserDto userDto) {
        return userService.save(UserMapper.toEntity(userDto));
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
}