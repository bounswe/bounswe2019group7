package com.eyetrade.backend.model.dto.transaction;

import com.eyetrade.backend.constants.CurrencyType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Emir Gökdemir
 * on 24 Kas 2019
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeTransactionDto {

    private CurrencyType boughtTypeCurrency;

    private CurrencyType soldTypeCurrency;

    private Double amountOfSold;
}
