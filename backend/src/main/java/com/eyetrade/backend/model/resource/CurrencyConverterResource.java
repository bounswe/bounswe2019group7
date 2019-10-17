package com.eyetrade.backend.model.resource;

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
public class CurrencyConverterResource {
    private Double amount;
    private Double rate;
}
