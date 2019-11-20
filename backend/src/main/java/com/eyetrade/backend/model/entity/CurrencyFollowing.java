package com.eyetrade.backend.model.entity;

import com.eyetrade.backend.constants.CurrencyType;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

import static com.eyetrade.backend.constants.GeneralConstants.ID_LENGTH;

/**
 * Created by Emir Gökdemir
 * on 2 Kas 2019
 */

@Data
@Entity
@Table(name="currency_following")
public class CurrencyFollowing {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "id", length = ID_LENGTH)
    private UUID id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "follower")
    private User follower;

    @NotNull
    @Column(name = "base_currency_type")
    private CurrencyType baseCurrencyType;

    @NotNull
    @Column(name = "Date")
    private String followingDate;

    @Column(name = "portfolio_id")
    private String portfolioID;

}
