package com.eyetrade.backend.model.dto.user;

import com.eyetrade.backend.constants.Role;
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
public class BasicRegisterDto {

    private String email;

    @Length(min = 5, message = "The field must be at least 5 characters")
    @Length(max = 50, message = "The field must be less than 50 characters")
    private String password;

    private String name;

    private String surname;

    @Length(min=10,max=13)
    private String phone;

    private double locationX;

    private double locationY;

    private String country;

    private String city;

}
