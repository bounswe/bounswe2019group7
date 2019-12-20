package com.eyetrade.backend.model.entity;

import com.eyetrade.backend.constants.CurrencyType;
import com.eyetrade.backend.constants.PredictionStatus;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

import static com.eyetrade.backend.constants.GeneralConstants.ID_LENGTH;

@Data
@Entity
@Table(name="prediction")
public class Prediction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "id", length = ID_LENGTH)
    private UUID id;

    @NotNull
    @Column(name = "predictor_id")
    private UUID predictorId;

    @NotNull
    @Column(name = "target_day_for_prediction")
    private String targetDayForPrediction;

    @NotNull
    @Column(name = "lower_bound_of_predicted_rate")
    private double lowerBoundOfPredictedRate;

    @NotNull
    @Column(name = "upper_bound_of_predicted_rate")
    private double upperBoundOfPredictedRate;

    @NotNull
    @Column(name = "dividend_currency_type")
    private CurrencyType dividendCurrencyType;

    @NotNull
    @Column(name = "divisor_currency_type")
    private CurrencyType divisorCurrencyType;

    @NotNull
    @Column(name = "prediction_date")
    private String predictionDate;

    @NotNull
    @Column(name = "status")
    private PredictionStatus status;

}
