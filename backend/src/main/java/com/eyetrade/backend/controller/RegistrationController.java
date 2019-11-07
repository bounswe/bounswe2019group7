package com.eyetrade.backend.controller;

import com.eyetrade.backend.model.dto.user.BasicUserDto;
import com.eyetrade.backend.model.dto.user.TraderUserDto;
import com.eyetrade.backend.model.resource.user.CompleteUserResource;
import com.eyetrade.backend.service.RegistrationService;
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

    @ApiOperation(value = "Register a basic user with the needed information", response = CompleteUserResource.class)
    @PostMapping("/basic_register")
    public ResponseEntity<CompleteUserResource> registerBasicUser(@RequestBody @Valid BasicUserDto basicUserDto,
                                                                  @RequestHeader("password") String password) {
        CompleteUserResource user = registrationService.saveBasicUser(basicUserDto, password);
        return ResponseEntity.ok(user);
    }

    @ApiOperation(value = "Register a trader user with the needed information", response = CompleteUserResource.class)
    @PostMapping("/trader_register")
    public ResponseEntity<CompleteUserResource> registerTraderUser(@RequestBody @Valid TraderUserDto traderRegisterDto,
                                                                   @RequestHeader("password") String password) {
        CompleteUserResource user = registrationService.saveTraderUser(traderRegisterDto, password);
        return ResponseEntity.ok(user);
    }

    @ApiOperation(value = "Confirm a registration by using the link from the user's confirmation mail", response = String.class)
    @GetMapping("/confirm_register")
    public ResponseEntity<String> confirmRegister(@RequestParam("token") String confirmationToken) {
        registrationService.confirmRegister(confirmationToken);
        return ResponseEntity.ok("Success");
    }


}