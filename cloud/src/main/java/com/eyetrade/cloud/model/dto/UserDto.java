package com.eyetrade.cloud.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

/**
 * Created by Emir Gökdemir
 * on 12 Eki 2019
 */
@Getter
@Setter
@ToString
public class UserDto {

    private String email;
    private String name;
    private String surname;
    private String city;
    private String locationX;
    private String locationY;

    @Length(min = 5, message = "The field must be at least 5 characters")
    @Length(max = 50, message = "The field must be less than 50 characters")
    private String password;
}
