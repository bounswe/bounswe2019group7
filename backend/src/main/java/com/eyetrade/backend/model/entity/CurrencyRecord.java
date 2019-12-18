package com.eyetrade.backend.model.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

import java.util.UUID;

import static com.eyetrade.backend.constants.GeneralConstants.ID_LENGTH;


@Data
@Entity
@Table(name="currency_records")
public class CurrencyRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "id", length = ID_LENGTH)
    private UUID id;

    @Column(name = "date")
    private String date; // time in milliseconds

    // Those are rates of the currencies with respect to base EUR
    // However, the base is not important in conversions
    // The iso codes of the currencies are in the comments below
    @Column(name = "turkish_lira")
    private Double turkishLiraRate; // TRY

    @Column(name = "dollar_rate")
    private Double dollarRate; // USD

    @Column(name = "euro_rate")
    private Double euroRate; // EUR

    @Column(name = "sterling_rate")
    private Double sterlingRate; //GBP

    @Column(name = "japan_rate")
    private Double japanRate; //JPY

    @Column(name = "china_rate")
    private Double chinaRate; //CNY

    // The followings are rates of the crypto currencies with respect to EUR
    @Column(name = "bitcoin")
    private Double bitcoin; // BTC

    @Column(name = "ethereum")
    private Double ethereum; // ETH

    @Column(name = "ripple")
    private Double ripple; // XRP

    @Column(name = "litecoin")
    private Double litecoin; // LTC

    @Column(name = "monero")
    private Double monero; // XMR

}
