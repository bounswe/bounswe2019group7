package com.eyetrade.backend.model.resource.portfolio;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.Resource;
import java.util.List;

@Resource
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PortfoliosResource {

    private List<PortfolioResource> portfolios;
}
