package com.eyetrade.backend.model.resource.portfolio;

import com.eyetrade.backend.constants.CurrencyType;
import lombok.*;

import javax.annotation.Resource;

@Resource
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioCurrencyPair {

    private CurrencyType baseType;
    private CurrencyType targetType;
    private double rate;

}
