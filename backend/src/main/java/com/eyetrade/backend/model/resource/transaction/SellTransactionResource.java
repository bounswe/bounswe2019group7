package com.eyetrade.backend.model.resource.transaction;

import com.eyetrade.backend.constants.CurrencyType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Emir Gökdemir
 * on 24 Kas 2019
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SellTransactionResource {

    private CurrencyType soldTypeCurrency;

    private double soldTypeInitialAmount;

    private double soldTypeLastAmount;

    private Boolean isSuccessful;

    public SellTransactionResource(CurrencyType soldTypeCurrency){
        this.soldTypeCurrency=soldTypeCurrency;
    }

}
