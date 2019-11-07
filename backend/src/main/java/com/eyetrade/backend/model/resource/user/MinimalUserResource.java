package com.eyetrade.backend.model.resource;

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
    // TODO: that model should be improved

    private String email;
    private boolean confirmed;
    private String name;
    private String surname;

}
