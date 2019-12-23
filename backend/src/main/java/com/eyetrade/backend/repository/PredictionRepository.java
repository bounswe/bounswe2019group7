package com.eyetrade.backend.repository;

import com.eyetrade.backend.model.entity.Prediction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PredictionRepository extends JpaRepository<Prediction, UUID> {

    Prediction findPredictionById(UUID id);

    List<Prediction> findPredictionByTargetDayForPrediction(String targetDate);

    List<Prediction> findPredictionByPredictorId(UUID predictorId);

}
