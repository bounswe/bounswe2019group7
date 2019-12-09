package com.eyetrade.backend.model.dto.currency;

import com.eyetrade.backend.constants.CryptoCurrencyType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CryptoCurrencyConverterLastDaysDto {

    private Integer dayCount;
    private CryptoCurrencyType source;
    private CryptoCurrencyType target;
    private Double amount;

}
