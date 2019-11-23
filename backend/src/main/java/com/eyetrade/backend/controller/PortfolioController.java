package com.eyetrade.backend.controller;

import com.eyetrade.backend.constants.CurrencyType;
import com.eyetrade.backend.model.resource.currency.CurrencyFollowingResource;
import com.eyetrade.backend.model.resource.portfolio.PortfolioResource;
import com.eyetrade.backend.model.resource.portfolio.PortfoliosResource;
import com.eyetrade.backend.security.JwtUserChecker;
import com.eyetrade.backend.service.PortfolioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Api(value = "Portfolio", tags = {"Portfolio related operations"})
@RestController
@RequestMapping("/portfolio")
public class PortfolioController {

    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private JwtUserChecker jwtUserChecker;

    @ApiOperation(value = "Create a portfolio",response = PortfolioResource.class)
    @PostMapping("/create")
    public ResponseEntity<PortfolioResource> createPortfolio(
            @RequestHeader("Authorization") String token,
            @RequestParam String portfolioName
    )throws IllegalAccessException{
        UUID ownerID = jwtUserChecker.resolveBasicToken(token);
        PortfolioResource portfolio = portfolioService.createPortfolio(portfolioName,ownerID);
        return ResponseEntity.ok(portfolio);
    }

    @ApiOperation(value = "Add currency to the portfolio", response = PortfolioResource.class)
    @PostMapping("add_currency")
    public ResponseEntity<PortfolioResource> addCurrency(
            @RequestHeader("Authorization") String token,
            @RequestHeader("BaseCurrencyType") CurrencyType baseCurrencyType,
            @RequestParam UUID portfolioID
    )throws IllegalAccessException{
        UUID ownerID = jwtUserChecker.resolveBasicToken(token);
        PortfolioResource portfolio = portfolioService.addCurrency(ownerID,baseCurrencyType,portfolioID);
        return ResponseEntity.ok(portfolio);
    }

    @ApiOperation(value = "Get current users portfolios", response = PortfoliosResource.class)
    @GetMapping("/get_portfolios")
    public ResponseEntity<PortfoliosResource> getPortfolios(
            @RequestHeader("Authorization") String token
    )throws IllegalAccessException{
        UUID ownerID = jwtUserChecker.resolveBasicToken(token);
        PortfoliosResource portfolios = portfolioService.getPortfolios(ownerID);
        return ResponseEntity.ok(portfolios);
    }

    @ApiOperation(value = "Get currency followings in the portfolio", response = CurrencyFollowingResource.class)
    @GetMapping("/get_currencies")
    public ResponseEntity<CurrencyFollowingResource> getCurrencies(
            @RequestHeader("Authorization") String token,
            @RequestParam String portfolioID
    )throws IllegalAccessException{
        jwtUserChecker.resolveBasicToken(token);
        return ResponseEntity.ok(portfolioService.getCurrencies(portfolioID));
    }
}
