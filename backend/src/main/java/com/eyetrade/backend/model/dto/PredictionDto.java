package com.eyetrade.backend.model.dto;

import com.eyetrade.backend.constants.CurrencyType;
import lombok.Data;

@Data
public class PredictionDto {

    private String targetDayForPrediction;

    private double lowerBoundOfPredictedRate;

    private double upperBoundOfPredictedRate;

    private CurrencyType dividendCurrencyType;

    private CurrencyType divisorCurrencyType;

}
