package com.eyetrade.cloud.model.resource;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Resource;

/**
 * Created by Emir Gökdemir
 * on 12 Eki 2019
 */

@Getter
@Setter
@Resource
public class UserResource {
    private String identifier;
    private String email;
    private boolean confirmed;
    private String name;
    private String surname;
}
