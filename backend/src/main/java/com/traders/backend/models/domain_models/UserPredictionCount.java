package com.traders.backend.models.domain_models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name="UserPredictionCount")
public class UserPredictionCount implements Serializable {

    @Id
    @OneToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "id"))
    public User user;

    @NotNull
    public Integer totalPredictions;

    @NotNull
    public Integer truePredictions;

    public UserPredictionCount(User user){
        this.user = user;
        this.totalPredictions = 0;
        this.truePredictions = 0;
        // the total and true predictions are set to 0 initially
    }

}
