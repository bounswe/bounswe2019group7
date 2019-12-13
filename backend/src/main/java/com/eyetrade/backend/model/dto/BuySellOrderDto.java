package com.eyetrade.backend.model.dto;

import com.eyetrade.backend.constants.CurrencyType;

import java.util.UUID;

/**
 * Created by Emir Gökdemir
 * on 13 Ara 2019
 */

public class BuySellOrderDto {

    private Double minRate;

    private Double maxRate;

    private Double boughtAmount;

    private CurrencyType boughtType;

    //they will be null if only buy or only sell
    private Double soldAmount;
    private CurrencyType soldType;
}
