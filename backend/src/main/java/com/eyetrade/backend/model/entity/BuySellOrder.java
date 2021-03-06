package com.eyetrade.backend.model.entity;

import com.eyetrade.backend.constants.CurrencyType;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

/**
 * Created by Emir Gökdemir
 * on 13 Ara 2019
 */

@Data
@Entity
@Table(name = "buy_sell_order")
public class BuySellOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "id")
    private UUID id;

    private Double minRate=0.0;

    private Double maxRate=1000000.0;

    private Double boughtAmount=null;

    @Enumerated(EnumType.STRING)
    private CurrencyType boughtType=null;

    //they will be null if only buy or only sell
    private Double soldAmount=null;
    @Enumerated(EnumType.STRING)
    private CurrencyType soldType=null;

    private Boolean isCompleted=false;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
