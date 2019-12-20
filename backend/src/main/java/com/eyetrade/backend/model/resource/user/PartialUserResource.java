package com.eyetrade.backend.model.resource.user;

import com.eyetrade.backend.constants.Role;
import com.eyetrade.backend.model.resource.prediction.PredictionCountResource;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Resource;

@Getter
@Setter
@Resource
public class PartialUserResource extends MinimalUserResource {

    private double locationX;

    private double locationY;

    private String country;

    private String city;

    private Role role;

    private long followerCount;

    private long followingCount;

    private PredictionCountResource predictionCount;

}
