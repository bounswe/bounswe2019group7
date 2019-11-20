package com.eyetrade.backend.controller;

import com.eyetrade.backend.model.resource.portfolio.PortfolioResource;
import com.eyetrade.backend.model.resource.portfolio.PortfoliosResource;
import com.eyetrade.backend.security.JwtUserChecker;
import com.eyetrade.backend.service.PortfolioService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Portfolio", tags = {"Portfolio related operations"})
@RestController
@RequestMapping("/portfolio")
public class PortfolioController {

    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private JwtUserChecker jwtUserChecker;

    public ResponseEntity<PortfolioResource> createPortfolio(){

    }

    public ResponseEntity<PortfolioResource> updatePortfolio(){

    }

    public ResponseEntity<PortfolioResource> getPortfolio(){

    }

    public ResponseEntity<PortfoliosResource> getPortfolios(){

    }

    public ResponseEntity<PortfoliosResource> getSelfPortfolios(){

    }
}
