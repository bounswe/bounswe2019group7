package com.eyetrade.backend.controller;

import com.eyetrade.backend.model.dto.PredictionDto;
import com.eyetrade.backend.model.resource.prediction.PredictionResource;
import com.eyetrade.backend.model.resource.prediction.PredictionsResource;
import com.eyetrade.backend.security.JwtUserChecker;
import com.eyetrade.backend.service.PredictionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Api(value = "Prediction", tags = {"Predictions"})
@RestController
@RequestMapping("/prediction")
public class PredictionController {

    @Autowired
    private PredictionService predictionService;

    @Autowired
    private JwtUserChecker jwtUserChecker;

    @ApiOperation(value = "Create a prediction", response = PredictionResource.class)
    @PostMapping("/create")
    public ResponseEntity createPrediction(@RequestBody @Valid PredictionDto dto,
                                           @RequestHeader("Authorization") String token){
        try{
            UUID userId = jwtUserChecker.resolveBasicToken(token);
            PredictionResource prediction = predictionService.makePrediction(dto, userId);
            return ResponseEntity.ok(prediction);
        }
        catch (IllegalAccessException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation(value = "Delete a prediction", response = String.class)
    @DeleteMapping("/delete")
    public ResponseEntity deletePrediction(@RequestHeader("predictionId") UUID predictionId,
                                           @RequestHeader("Authorization") String token){
        try{
            UUID userId = jwtUserChecker.resolveBasicToken(token);
            predictionService.deletePrediction(predictionId, userId);
            return ResponseEntity.ok("Success");
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation(value = "Get all of the predictions of self", response = PredictionsResource.class)
    @GetMapping("/get_self_predictions")
    public ResponseEntity getSelfPredictions(@RequestHeader("Authorization") String token){
        try{
            UUID userId = jwtUserChecker.resolveBasicToken(token);
            PredictionsResource resource =  predictionService.getPredictionsOfSelf(userId);
            return ResponseEntity.ok(resource);
        }
        catch (IllegalAccessException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation(value = "Get all of the predictions of another user", response = PredictionsResource.class)
    @GetMapping("/get_user_predictions")
    public ResponseEntity getAnotherUserPredictions(@RequestHeader("otherUserEmail") String otherUserEmail,
                                                    @RequestHeader("Authorization") String token){
        try{
            jwtUserChecker.resolveBasicToken(token);
            PredictionsResource resource =  predictionService.getPredictionsOfUser(otherUserEmail);
            return ResponseEntity.ok(resource);
        }
        catch (IllegalAccessException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
