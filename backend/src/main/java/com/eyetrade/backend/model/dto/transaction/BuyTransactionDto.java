package com.eyetrade.backend.model.dto.transaction;

import com.eyetrade.backend.constants.CurrencyType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Emir Gökdemir
 * on 24 Kas 2019
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BuyTransactionDto {

    private CurrencyType boughtTypeCurrency;

    private String creditCardNumber;

    private String cvv;

    private String expiredDate;

    private double amount;


    public BuyTransactionDto(CurrencyType boughtTypeCurrency, double amount) {
        this.boughtTypeCurrency = boughtTypeCurrency;
        this.amount = amount;
    }

}

