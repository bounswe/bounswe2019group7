package com.traders.backend.models.domain_models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name="AccountPredictionCount")
public class AccountPredictionCount implements Serializable {

    @Id
    @OneToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "id"))
    public Account account;

    @NotNull
    public Integer totalPredictions;

    @NotNull
    public Integer truePredictions;

    public AccountPredictionCount(Account account){
        this.account = account;
        this.totalPredictions = 0;
        this.truePredictions = 0;
        // the total and true predictions are set to 0 initially
    }

}
