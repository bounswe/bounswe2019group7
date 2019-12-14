package com.eyetrade.backend.model.resource.transaction;

import com.eyetrade.backend.constants.CurrencyType;
import com.eyetrade.backend.model.resource.user.MinimalUserResource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * Created by Emir Gökdemir
 * on 13 Ara 2019
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Resource
public class BuySellOrderResource {
    private UUID id;

    private Double minRate;

    private Double maxRate;

    private Double boughtAmount;

    private CurrencyType boughtType;

    private MinimalUserResource user;

    //they will be null if only buy or only sell
    private Double soldAmount;
    private CurrencyType soldType;
}
