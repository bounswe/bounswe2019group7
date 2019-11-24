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
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyConverterLastDaysDto {
    private Integer lastDays;

    private CurrencyType sourceCurrencyType;
    private CurrencyType targetCurrencyType;

    private Float amount;
}
