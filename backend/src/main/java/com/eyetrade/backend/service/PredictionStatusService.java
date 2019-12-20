package com.eyetrade.backend.service;

import com.eyetrade.backend.constants.GeneralConstants;
import com.eyetrade.backend.constants.PredictionStatus;
import com.eyetrade.backend.model.entity.CurrencyRecord;
import com.eyetrade.backend.model.entity.Prediction;
import com.eyetrade.backend.model.entity.PredictionCountOfUser;
import com.eyetrade.backend.repository.PredictionCountRepository;
import com.eyetrade.backend.repository.PredictionRepository;
import com.eyetrade.backend.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class PredictionStatusService {

    @Autowired
    private PredictionRepository predictionRepository;

    @Autowired
    private PredictionCountRepository predictionCountRepository;

    @Autowired
    private CurrencyRateService currencyRateService;

    @Scheduled(cron = "0 0 7 * * ?") // every day at 7 am.
    public void updatePredictionStatuses(){
        CurrencyRecord todayRecord = currencyRateService.findLastRecord();
        String today = DateUtils.dateTimeFormatter(new Date(), GeneralConstants.DB_DATE_TIME_FORMAT);
        List<Prediction> predictionsToEvaluate = predictionRepository.findPredictionByTargetDayForPrediction(today);
        for(Prediction prediction : predictionsToEvaluate){
            evaluatePrediction(prediction, todayRecord);
        }
    }

    private void evaluatePrediction(Prediction prediction, CurrencyRecord todayRecord) {
        double dividendValue = currencyRateService.findRate(prediction.getDividendCurrencyType(), todayRecord);
        double divisorValue = currencyRateService.findRate(prediction.getDivisorCurrencyType(), todayRecord);
        double realRate = dividendValue / divisorValue;
        if(realRate >= prediction.getLowerBoundOfPredictedRate() && realRate <= prediction.getUpperBoundOfPredictedRate()){
            prediction.setStatus(PredictionStatus.success);
        }
        else{
            prediction.setStatus(PredictionStatus.failed);
        }
        predictionRepository.saveAndFlush(prediction); // update the prediction record
        updatePredictionCount(prediction.getPredictorId(), prediction.getStatus());
    }

    private void updatePredictionCount(UUID predictorId, PredictionStatus status) {
        PredictionCountOfUser predictionCount = predictionCountRepository.findPredictionCountOfUserByUserId(predictorId);
        // obviously the future count will be decreased by one
        predictionCount.setFutureCount(predictionCount.getFutureCount() - 1);
        if(status == PredictionStatus.success){
            predictionCount.setSuccessCount(predictionCount.getSuccessCount() + 1);
        }
        else{
            predictionCount.setFailCount(predictionCount.getFailCount() + 1);
        }
        predictionCountRepository.saveAndFlush(predictionCount);
    }

}
