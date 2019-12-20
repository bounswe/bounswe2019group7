package com.eyetrade.backend.model.resource.prediction;

import lombok.Data;

import javax.annotation.Resource;
import java.util.List;

@Resource
@Data
public class PredictionsResource {

    private List<PredictionResource> successfulPredictions;

    private List<PredictionResource> failedPredictions;

    private List<PredictionResource> futurePredictions;

}
