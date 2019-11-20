package com.eyetrade.backend.model.resource.portfolio;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.Resource;
import java.util.UUID;

@Resource
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioResource {
    private UUID uuid;
    private String name;
    private String ownerID;
    private String currencies;
}
