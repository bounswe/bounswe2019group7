package com.eyetrade.backend.model.resource.user;

import com.eyetrade.backend.constants.PrivacyType;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Resource;

@Getter
@Setter
@Resource
public class CompleteUserResource extends PartialUserResource{

    private String phone;

    private PrivacyType privacyType;

    private String iban;

    private String identityNo;

}
