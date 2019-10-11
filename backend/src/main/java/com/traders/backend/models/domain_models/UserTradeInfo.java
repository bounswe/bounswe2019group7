package com.traders.backend.models.domain_models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "UserTradeInfo")
public class UserTradeInfo implements Serializable {

    @Id
    @OneToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "id"))
    public User user;

    @NotNull
    public String iban;

    @NotNull
    public String tc;

    public UserTradeInfo(User user, String iban, String tc){
        this.user = user;
        this.iban = iban;
        this.tc = tc;
    }

}
