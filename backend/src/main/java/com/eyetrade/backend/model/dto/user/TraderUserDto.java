package com.eyetrade.backend.model.dto.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@ToString
public class TraderRegisterDto extends BasicRegisterDto {

    @Length(min=16,max=18)
    private String iban;

    @Length(min=11, max=11)
    private String identityNo;

}
