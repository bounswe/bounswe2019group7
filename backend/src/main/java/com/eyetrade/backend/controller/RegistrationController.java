package com.eyetrade.backend.controller;

import com.eyetrade.backend.model.dto.UserDto;
import com.eyetrade.backend.model.resource.UserResource;
import com.eyetrade.backend.service.RegistrationService;
import com.eyetrade.backend.mapper.UserMapper;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/** Created by Emir Gökdemir
 on 12 Eki 2019 */

@RestController
@RequestMapping(value = {"/registration"})
@Api
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @PostMapping("/register")
    public UserResource registerUser(@RequestBody @Valid UserDto userDto) {
        return registrationService.save(UserMapper.dtoToEntity(userDto));
    }

    @GetMapping("/confirm-register")
    public ResponseEntity<String> confirmRegister(@RequestParam("token") String confirmationToken) {
        registrationService.confirmRegister(confirmationToken);
        return ResponseEntity.ok("Success");
    }


}