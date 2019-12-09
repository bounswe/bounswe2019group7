package com.eyetrade.backend.model.resource.currency;

import com.eyetrade.backend.constants.CryptoCurrencyType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CryptoCurrencyConverterResource {

    private Double amount;
    private Double rate;
    private String date;
    private CryptoCurrencyType sourceType;
    private CryptoCurrencyType targetType;

}
