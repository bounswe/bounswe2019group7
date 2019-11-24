package com.eyetrade.backend.model.entity;

import com.oracle.webservices.internal.api.EnvelopeStyle;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import java.math.BigDecimal;
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
    private User userId;

    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    private double TRY=0;

    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    private double USD=0;

    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    private double EUR=0;

    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    private double CNY=0;

    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    private double JPY=0;

    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    private double GBP=0;
}
