package com.eyetrade.backend.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name="currency_records")
public class CurrencyRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "timestamp")
    private Long timestamp; // time in milliseconds

    // Those are rates of the currencies with respect to base EUR
    // However, the base is not important in conversions
    // The iso codes of the currencies are in the comments below
    @Column(name = "turkish_lira")
    private Double turkish_lira; // TRY

    @Column(name = "dollar")
    private Double dollar; // USD

    @Column(name = "euro")
    private Double euro; // EUR

    @Column(name = "sterling")
    private Double sterling; //GBP
}
