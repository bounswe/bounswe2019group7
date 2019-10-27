package com.eyetrade.backend.controller;

import com.eyetrade.backend.model.dto.UserDto;
import com.eyetrade.backend.model.resource.UserResource;
import com.eyetrade.backend.service.RegistrationService;
import com.eyetrade.backend.mapper.UserMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/** Created by Emir Gökdemir
 on 12 Eki 2019 */

@RestController
@RequestMapping(value = {"/registration"})
@Api(value = "Registration", tags = {"Operations Related With Registration"})
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @ApiOperation(value = "Register a user with the needed information", response = UserResource.class)
    @PostMapping("/register")
    public ResponseEntity<UserResource> registerUser(@RequestBody @Valid UserDto userDto) {
        UserResource user = registrationService.save(UserMapper.dtoToEntity(userDto));
        return ResponseEntity.ok(user);
    }

    @ApiOperation(value = "Confirm a registration by using the link from the user's confirmation mail", response = String.class)
    @GetMapping("/confirm-register")
    public ResponseEntity<String> confirmRegister(@RequestParam("token") String confirmationToken) {
        registrationService.confirmRegister(confirmationToken);
        return ResponseEntity.ok("Success");
    }


}