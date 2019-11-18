package com.eyetrade.backend.model.resource.currency;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Emir Gökdemir
 * on 18 Kas 2019
 */

@Resource
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyIntervalResource {
    List<CurrencyConverterResource> currencyConverterResources;
    
}
