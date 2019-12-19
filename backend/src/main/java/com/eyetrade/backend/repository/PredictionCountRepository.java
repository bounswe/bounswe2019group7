package com.eyetrade.backend.repository;

import com.eyetrade.backend.model.entity.PredictionCountOfUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PredictionCountRepository extends JpaRepository<PredictionCountOfUser, UUID> {

    PredictionCountOfUser findPredictionCountOfUserByUserId(UUID userId);

}
