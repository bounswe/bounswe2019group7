package com.eyetrade.backend.model.resource.transaction;

import com.eyetrade.backend.constants.CurrencyType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.Resource;

/**
 * Created by Emir Gökdemir
 * on 24 Kas 2019
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Resource
public class TransactionResource {

    private CurrencyType boughtTypeCurrency;

    private CurrencyType soldTypeCurrency;

    private float boughtTypeInitialAmount;

    private float soldTypeInitialAmount;

    private float boughtTypeLastAmount;

    private float soldTypeLastAmount;

    private float rate;
}
