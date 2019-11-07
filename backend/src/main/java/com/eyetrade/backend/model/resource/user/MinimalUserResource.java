package com.eyetrade.backend.model.resource.user;

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
public class MinimalUserResource {

    private String email;

    private String name;

    private String surname;

}
