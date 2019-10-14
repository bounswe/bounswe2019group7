package com.traders.backend.models.data_transfer_models;

import java.io.Serializable;

public class JwtResponse implements Serializable {

    public String jwttoken;

    public JwtResponse(String token){
        this.jwttoken = token;
    }

}
