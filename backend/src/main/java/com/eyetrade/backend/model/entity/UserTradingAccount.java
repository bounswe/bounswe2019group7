package com.eyetrade.backend.model.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import java.util.UUID;

import static com.eyetrade.backend.constants.GeneralConstants.ID_LENGTH;
/**
 * Created by Emir Gökdemir
 * on 24 Kas 2019
 */
@Data
@Entity
@Table(name="user_account")
public class UserTradingAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "id", length = ID_LENGTH)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Double tryAmount=0.0;

    private Double usdAmount=0.0;

    private Double eurAmount=0.0;

    private Double cnyAmount=0.0;

    private Double jpyAmount=0.0;

    private Double gbpAmount=0.0;

    private Double bitcoinAmount=0.0; // BTC

    private Double ethereumAmount=0.0; // ETH

    private Double rippleAmount=0.0; // XRP

    private Double litecoinAmount=0.0; // LTC

    private Double moneroAmount=0.0; // XMR
}
