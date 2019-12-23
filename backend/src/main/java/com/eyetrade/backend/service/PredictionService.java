package com.eyetrade.backend.service;

import com.eyetrade.backend.constants.ErrorConstants;
import com.eyetrade.backend.constants.PredictionStatus;
import com.eyetrade.backend.mapper.PredictionMapper;
import com.eyetrade.backend.model.dto.PredictionDto;
import com.eyetrade.backend.model.entity.Prediction;
import com.eyetrade.backend.model.entity.PredictionCountOfUser;
import com.eyetrade.backend.model.resource.prediction.PredictionResource;
import com.eyetrade.backend.model.resource.prediction.PredictionsResource;
import com.eyetrade.backend.repository.PredictionCountRepository;
import com.eyetrade.backend.repository.PredictionRepository;
import com.eyetrade.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PredictionService {

    @Autowired
    private PredictionRepository predictionRepository;

    @Autowired
    private PredictionCountRepository predictionCountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CurrencyRateService currencyRateService;

    @Transactional
    public PredictionResource makePrediction(PredictionDto dto, UUID userId){
        if(dto.getLowerBoundOfPredictedRate() > dto.getUpperBoundOfPredictedRate()){
            throw new IllegalArgumentException(ErrorConstants.PREDICTION_INTERVAL_INVALID);
        }
        Prediction prediction = PredictionMapper.dtoToEntity(dto, userId);
        predictionRepository.saveAndFlush(prediction);
        PredictionCountOfUser predictionCount = predictionCountRepository.findPredictionCountOfUserByUserId(userId);
        predictionCount.setFutureCount(predictionCount.getFutureCount() + 1);
        predictionCountRepository.saveAndFlush(predictionCount);
        return PredictionMapper.entityToResource(prediction);
    }

    @Transactional
    public void deletePrediction(UUID predictionId, UUID userId) throws IllegalAccessException {
        Prediction prediction = predictionRepository.findPredictionById(predictionId);
        if(!prediction.getPredictorId().equals(userId)){
            throw new IllegalAccessException(ErrorConstants.CAN_NOT_DELETE_OTHER_USERS_PREDICTION);
        }
        if(!prediction.getStatus().equals(PredictionStatus.future)){
            throw new IllegalAccessException(ErrorConstants.CAN_NOT_DELETE_EVALUATED_PREDICTION);
        }
        predictionRepository.deleteById(predictionId);
        PredictionCountOfUser predictionCount = predictionCountRepository.findPredictionCountOfUserByUserId(userId);
        predictionCount.setFutureCount(predictionCount.getFutureCount() - 1);
        predictionCountRepository.saveAndFlush(predictionCount);
    }

    public PredictionsResource getPredictionsOfSelf(UUID userId){
        return getPredictions(userId);
    }

    public PredictionsResource getPredictionsOfUser(String email){
        UUID userId;
        try{
            userId = userRepository.findByEmail(email).getId();
        }
        catch (Exception e){
            throw new IllegalArgumentException(ErrorConstants.USER_NOT_EXIST);
        }
        return getPredictions(userId);
    }

    private PredictionsResource getPredictions(UUID userId){
        List<Prediction> predictions = predictionRepository.findPredictionByPredictorId(userId);
        List<PredictionResource> resources = new ArrayList<>();
        for(Prediction prediction : predictions){
            resources.add(PredictionMapper.entityToResource(prediction));
        }
        return PredictionMapper.resourceListToResourcesObject(resources);
    }

}
