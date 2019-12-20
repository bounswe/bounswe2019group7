package com.eyetrade.backend.service;

import com.eyetrade.backend.repository.PredictionCountRepository;
import com.eyetrade.backend.repository.PredictionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PredictionService {

    @Autowired
    private PredictionRepository predictionRepository;

    @Autowired
    private PredictionCountRepository predictionCountRepository;

    @Autowired
    private CurrencyRateService currencyRateService;



}
