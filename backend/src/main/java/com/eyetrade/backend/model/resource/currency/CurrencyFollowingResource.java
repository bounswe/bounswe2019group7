package com.eyetrade.backend.model.resource.currency;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.Resource;

/**
 * Created by Emir Gökdemir
 * on 2 Kas 2019
 */

@Resource
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyFollowingResource {

    private Double turkishLiraRate; // TRY
    private Double dollarRate; // USD
    private Double euroRate; // EUR
    private Double sterlingRate; //GBP

}
