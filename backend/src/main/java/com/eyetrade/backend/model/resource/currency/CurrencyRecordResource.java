package com.eyetrade.backend.model.resource.currency;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;

import javax.annotation.Resource;

/**
 * Created by Emir Gökdemir
 * on 25 Kas 2019
 */
@Resource
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CurrencyRecordResource {
    private String date; // time in milliseconds

    private Double turkishLiraRate; // TRY

    private Double dollarRate; // USD

    private Double euroRate; // EUR

    private Double sterlingRate; //GBP

    private Double japanRate; //JPY

    private Double chinaRate; //CNY

}
