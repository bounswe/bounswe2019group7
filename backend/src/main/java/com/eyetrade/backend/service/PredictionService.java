package com.eyetrade.backend.service;

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
        Prediction prediction = PredictionMapper.dtoToEntity(dto, userId);
        predictionRepository.saveAndFlush(prediction);
        PredictionCountOfUser predictionCount = predictionCountRepository.findPredictionCountOfUserByUserId(userId);
        predictionCount.setFutureCount(predictionCount.getFutureCount() + 1);
        predictionCountRepository.saveAndFlush(predictionCount);
        return PredictionMapper.entityToResource(prediction);
    }

    @Transactional
    public void deletePrediction(UUID predictionId, UUID userId){
        predictionRepository.deleteById(predictionId);
        PredictionCountOfUser predictionCount = predictionCountRepository.findPredictionCountOfUserByUserId(userId);
        predictionCount.setFutureCount(predictionCount.getFutureCount() - 1);
        predictionCountRepository.saveAndFlush(predictionCount);
    }

    public PredictionsResource getPredictionsOfSelf(UUID userId){
        return getPredictions(userId);
    }

    public PredictionsResource getPredictionsOfUser(String email){
        UUID userId = userRepository.findByEmail(email).getId();
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
