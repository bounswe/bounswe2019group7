package com.eyetrade.backend.model.resource.portfolio;

import com.eyetrade.backend.constants.CurrencyType;
import lombok.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Resource
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioResource {

    private UUID id;
    private String name;
    private UUID ownerId;
    private List<PortfolioCurrencyPair> currencyPairs;

}
