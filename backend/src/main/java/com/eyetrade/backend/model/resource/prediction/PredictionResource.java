package com.eyetrade.backend.model.resource.prediction;

import com.eyetrade.backend.constants.CurrencyType;
import com.eyetrade.backend.constants.PredictionStatus;
import lombok.Data;

import javax.annotation.Resource;
import java.util.UUID;

@Resource
@Data
public class PredictionResource {

    private UUID id;

    private String targetDayForPrediction;

    private double lowerBoundOfPredictedRate;

    private double upperBoundOfPredictedRate;

    private CurrencyType dividendCurrencyType;

    private CurrencyType divisorCurrencyType;

    private String predictionDate;

    private PredictionStatus predictionStatus;

}
