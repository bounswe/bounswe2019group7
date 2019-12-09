package com.eyetrade.backend.model.dto.currency;

import com.eyetrade.backend.constants.CryptoCurrencyType;
import com.eyetrade.backend.constants.CurrencyType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CryptoCurrencyConverterDto {

    private CryptoCurrencyType source;
    private CryptoCurrencyType target;
    private Double amount;

}
