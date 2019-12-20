package com.eyetrade.backend.mapper;

import com.eyetrade.backend.constants.GeneralConstants;
import com.eyetrade.backend.constants.PredictionStatus;
import com.eyetrade.backend.model.dto.PredictionDto;
import com.eyetrade.backend.model.entity.Prediction;
import com.eyetrade.backend.model.entity.PredictionCountOfUser;
import com.eyetrade.backend.model.resource.prediction.PredictionCountResource;
import com.eyetrade.backend.model.resource.prediction.PredictionResource;
import com.eyetrade.backend.model.resource.prediction.PredictionsResource;
import com.eyetrade.backend.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class PredictionMapper {

    public static Prediction dtoToEntity(PredictionDto dto, UUID predictorId){
        Prediction prediction = new Prediction();
        prediction.setPredictorId(predictorId);
        prediction.setTargetDayForPrediction(dto.getTargetDayForPrediction());
        prediction.setLowerBoundOfPredictedRate(dto.getLowerBoundOfPredictedRate());
        prediction.setUpperBoundOfPredictedRate(dto.getUpperBoundOfPredictedRate());
        prediction.setDividendCurrencyType(dto.getDividendCurrencyType());
        prediction.setDivisorCurrencyType(dto.getDivisorCurrencyType());
        prediction.setPredictionDate(DateUtils.dateTimeFormatter(new Date(), GeneralConstants.DB_DATE_TIME_FORMAT));
        prediction.setStatus(PredictionStatus.future);
        return prediction;
    }

    public static PredictionResource entityToResource(Prediction prediction){
        PredictionResource resource = new PredictionResource();
        resource.setId(prediction.getId());
        resource.setTargetDayForPrediction(prediction.getTargetDayForPrediction());
        resource.setLowerBoundOfPredictedRate(prediction.getLowerBoundOfPredictedRate());
        resource.setUpperBoundOfPredictedRate(prediction.getUpperBoundOfPredictedRate());
        resource.setDividendCurrencyType(prediction.getDividendCurrencyType());
        resource.setDivisorCurrencyType(prediction.getDivisorCurrencyType());
        resource.setPredictionDate(prediction.getPredictionDate());
        resource.setPredictionStatus(prediction.getStatus());
        return resource;
    }

    public static PredictionsResource resourceListToResourcesObject(List<PredictionResource> resourceList){
        List<PredictionResource> successfulPredictions = new ArrayList<>();
        List<PredictionResource> failedPredictions = new ArrayList<>();
        List<PredictionResource> futurePredictions = new ArrayList<>();
        for(PredictionResource resource : resourceList){
            if(resource.getPredictionStatus() == PredictionStatus.success){
                successfulPredictions.add(resource);
            }
            else if(resource.getPredictionStatus() == PredictionStatus.failed){
                failedPredictions.add(resource);
            }
            else{
                futurePredictions.add(resource);
            }
        }
        PredictionsResource predictionsObject = new PredictionsResource();
        predictionsObject.setSuccessfulPredictions(successfulPredictions);
        predictionsObject.setFailedPredictions(failedPredictions);
        predictionsObject.setFuturePredictions(futurePredictions);
        return predictionsObject;
    }

    public static PredictionCountResource predictionCountToResource(PredictionCountOfUser predictionCount){
        PredictionCountResource resource = new PredictionCountResource();
        resource.setSuccessCount(predictionCount.getSuccessCount());
        resource.setFailCount(predictionCount.getFailCount());
        resource.setFutureCount(predictionCount.getFutureCount());
        resource.setTotalCount(predictionCount.getSuccessCount() + predictionCount.getFailCount() + predictionCount.getFutureCount());
        return resource;
    }

    public static PredictionCountOfUser createPredictionCount(UUID userId){
        PredictionCountOfUser predictionCountOfUser = new PredictionCountOfUser();
        predictionCountOfUser.setUserId(userId);
        predictionCountOfUser.setSuccessCount(0);
        predictionCountOfUser.setFailCount(0);
        predictionCountOfUser.setFutureCount(0);
        return predictionCountOfUser;
    }

}
