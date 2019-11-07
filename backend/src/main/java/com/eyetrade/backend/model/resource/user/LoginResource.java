package com.eyetrade.backend.model.resource.user;

import lombok.Getter;

import javax.annotation.Resource;

@Getter
@Resource
public class LoginResource {

    private String token;

    public LoginResource(String token){
        this.token = token;
    }

}
