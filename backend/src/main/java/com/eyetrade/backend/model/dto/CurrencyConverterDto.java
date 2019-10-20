package com.eyetrade.backend.model.dto;

import com.eyetrade.backend.constants.CurrencyType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Emir Gökdemir
 * on 17 Eki 2019
 */
@Getter
@Setter
@NoArgsConstructor
public class CurrencyConverterDto {
    private CurrencyType inputCurrencyType;
    private CurrencyType outputCurrencyType;
    private Double amount;
}
