package com.eyetrade.backend.controller;

import com.eyetrade.backend.model.dto.transaction.BuyTransactionDto;
import com.eyetrade.backend.model.dto.transaction.ExchangeTransactionDto;
import com.eyetrade.backend.model.dto.transaction.SellTransactionDto;
import com.eyetrade.backend.model.resource.transaction.BuyTransactionResource;
import com.eyetrade.backend.model.resource.transaction.ExchangeTransactionResource;
import com.eyetrade.backend.model.resource.transaction.SellTransactionResource;
import com.eyetrade.backend.model.resource.transaction.UserAccountResource;
import com.eyetrade.backend.security.JwtResolver;
import com.eyetrade.backend.security.JwtUserChecker;
import com.eyetrade.backend.service.TransactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Created by Emir Gökdemir
 * on 24 Kas 2019
 */
@RestController
@Api(value = "Transactions", tags = {"Transaction, Buy Fund, Exchange Currencies"})
public class TransactionController {

    @Autowired
    private TransactionService service;

    @Autowired
    private JwtResolver jwtResolver;

    @Autowired
    private JwtUserChecker jwtUserChecker;

    @ApiOperation(value = "A trader user can create trading account.", response = UserAccountResource.class)
    @GetMapping("/create_trading_account")
    public ResponseEntity<UserAccountResource> createTradingAccount(@RequestHeader("Authorization") String token) throws IllegalAccessException{
        UUID userId=jwtUserChecker.resolveTraderToken(token);
        return ResponseEntity.ok(service.createTradingAccount(userId));
    }

    @ApiOperation(value = "A trader user can access his/her trading account.", response = UserAccountResource.class)
    @GetMapping("/get_trading_account")
    public ResponseEntity<UserAccountResource> getTradingAccount(@RequestHeader("Authorization") String token){
        UUID userId=jwtResolver.getIdFromToken(token);
        return ResponseEntity.ok(service.getUserTradingAccount(userId));
    }

    @ApiOperation(value = "A user can buy fund with dto.", response = BuyTransactionResource.class)
    @PostMapping("/buy_transaction")
    public ResponseEntity<BuyTransactionResource> buyFund(@RequestHeader("Authorization") String token,
                                                       @RequestBody BuyTransactionDto dto){
        UUID userId=jwtResolver.getIdFromToken(token);
        return ResponseEntity.ok(service.buyFund(dto,userId));
    }

    @ApiOperation(value = "A user can sell fund with dto.", response = SellTransactionResource.class)
    @PostMapping("/sell_transaction")
    public ResponseEntity<SellTransactionResource> buyFund(@RequestHeader("Authorization") String token,
                                                       @RequestBody SellTransactionDto dto){
        UUID userId=jwtResolver.getIdFromToken(token);
        return ResponseEntity.ok(service.selfund(dto,userId));
    }

    @ApiOperation(value = "A user can buy fund with dto.", response = ExchangeTransactionResource.class)
    @PostMapping("/exchange_transaction")
    public ResponseEntity<ExchangeTransactionResource> exchangeFunds(@RequestHeader("Authorization") String token,
                                                                     @RequestBody ExchangeTransactionDto dto){
        UUID userId=jwtResolver.getIdFromToken(token);
        return ResponseEntity.ok(service.exchangeFunds(dto,userId));
    }


}
