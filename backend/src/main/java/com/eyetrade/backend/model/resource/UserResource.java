package com.eyetrade.backend.model.resource;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * Created by Emir Gökdemir
 * on 12 Eki 2019
 */

@Getter
@Setter
@Resource
public class UserResource {
    private UUID id;
    private String email;
    private boolean confirmed;
    private String name;
    private String surname;
}
