package com.eyetrade.backend.model.dto.currency;

import com.eyetrade.backend.constants.CryptoCurrencyType;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CryptoCurrencyConverterIntervalDto {

    private String startDate;
    private String endDate;
    private CryptoCurrencyType source;
    private CryptoCurrencyType target;
    private Double amount;

}
