package com.eyetrade.cloud.model.dto;

import com.eyetrade.cloud.util.constants.Role;
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
    @Length(min=10,max=13)
    private String phone;
    @Length(min=16,max=18)
    private String iban;
    @Length(min=11, max=11)
    private String identityNo;
    private String city;
    private String locationX;
    private String locationY;
    private Role role;

    @Length(min = 5, message = "The field must be at least 5 characters")
    @Length(max = 50, message = "The field must be less than 50 characters")
    private String password;
}
