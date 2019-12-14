package com.eyetrade.backend.model.dto.user;

import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.annotation.Resource;

@Data
@Resource
@ToString
public class BasicToTraderDto {

    @Length(min=16,max=18)
    private String iban;

    @Length(min=11, max=11)
    private String identityNo;

}
