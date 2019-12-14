package com.eyetrade.backend.controller;

import com.eyetrade.backend.constants.CurrencyType;
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

@Api(value = "Portfolio", tags = {"Portfolios"})
@RestController
@RequestMapping("/portfolio")
public class PortfolioController {

    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private JwtUserChecker jwtUserChecker;

    @ApiOperation(value = "Create a portfolio", response = PortfolioResource.class)
    @PostMapping("/create")
    public ResponseEntity createPortfolio(
            @RequestHeader("Authorization") String token,
            @RequestParam String portfolioName
    ){
        try {
            UUID ownerID = jwtUserChecker.resolveBasicToken(token);
            PortfolioResource portfolio = portfolioService.createPortfolio(portfolioName, ownerID);
            return ResponseEntity.ok(portfolio);
        } catch (IllegalAccessException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation(value = "Delete a portfolio", response = PortfolioResource.class)
    @PostMapping("/delete")
    public ResponseEntity deletePortfolio(
            @RequestHeader("Authorization") String token,
            @RequestParam UUID portfolioId
    ){
        try {
            UUID ownerID = jwtUserChecker.resolveBasicToken(token);
            PortfolioResource portfolio = portfolioService.deletePortfolio(portfolioId, ownerID);
            return ResponseEntity.ok(portfolio);
        } catch (IllegalAccessException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // TODO : User check must be added later
    @ApiOperation(value = "Add currency to a portfolio", response = PortfolioResource.class)
    @PostMapping("add_currency_to_portfolio")
    public ResponseEntity addCurrency(
            @RequestHeader("Authorization") String token,
            @RequestHeader("BaseCurrencyType") CurrencyType baseCurrencyType,
            @RequestParam UUID portfolioID
    ){
        try {
            jwtUserChecker.resolveBasicToken(token);
            PortfolioResource portfolio = portfolioService.addCurrency(baseCurrencyType, portfolioID);
            return ResponseEntity.ok(portfolio);
        } catch (IllegalAccessException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation(value = "Get current user's portfolios", response = PortfoliosResource.class)
    @GetMapping("/get_self_portfolios")
    public ResponseEntity getPortfolios(
            @RequestHeader("Authorization") String token
    ){
        try {
            UUID ownerId = jwtUserChecker.resolveBasicToken(token);
            PortfoliosResource portfolios = portfolioService.getPortfolios(ownerId);
            return ResponseEntity.ok(portfolios);
        } catch (IllegalAccessException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
