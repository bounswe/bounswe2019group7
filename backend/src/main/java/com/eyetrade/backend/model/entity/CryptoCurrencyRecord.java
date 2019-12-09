package com.eyetrade.backend.model.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

import static com.eyetrade.backend.constants.GeneralConstants.ID_LENGTH;

@Data
@Entity
@Table(name="crypto_currency_records")
public class CryptoCurrencyRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "id", length = ID_LENGTH)
    private UUID id;

    @Column(name = "date")
    private String date;

    // The followings are rates of the crypto currencies with respect to USD
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
