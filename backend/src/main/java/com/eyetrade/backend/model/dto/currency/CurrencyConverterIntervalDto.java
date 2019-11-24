package com.eyetrade.backend.model.dto.currency;

import com.eyetrade.backend.constants.CurrencyType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Emir Gökdemir
 * on 18 Kas 2019
 */

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CurrencyConverterIntervalDto {
    private String startDate;
    private String endDate;

    private CurrencyType sourceCurrencyType;
    private CurrencyType targetCurrencyType;

    private Float amount;
}
