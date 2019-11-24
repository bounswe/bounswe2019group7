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
public class ExchangeTransactionResource {

    private CurrencyType boughtTypeCurrency;

    private CurrencyType soldTypeCurrency;

    private Double boughtTypeInitialAmount;

    private Double soldTypeInitialAmount;

    private Double boughtTypeLastAmount;

    private Double soldTypeLastAmount;

    private Double rate;
}
