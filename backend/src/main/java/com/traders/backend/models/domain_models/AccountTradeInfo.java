package com.traders.backend.models.domain_models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "AccountTradeInfo")
public class AccountTradeInfo implements Serializable {

    @Id
    @OneToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "id"))
    public Account account;

    @NotNull
    public String iban;

    @NotNull
    public String tc;

    public AccountTradeInfo(Account account, String iban, String tc){
        this.account = account;
        this.iban = iban;
        this.tc = tc;
    }

}
