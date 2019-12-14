package com.eyetrade.backend.model.resource.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.annotation.Resource;
import java.util.UUID;

@Getter
@Resource
@AllArgsConstructor
public class LoginResource {

    private String token;

    private UUID userId;

}
