package com.eyetrade.backend.model.dto.transaction;

import com.eyetrade.backend.constants.CurrencyType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Emir Gökdemir
 * on 13 Ara 2019
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SellTransactionDto {
    private CurrencyType soldTypeCurrency;
    private double amount;
}
