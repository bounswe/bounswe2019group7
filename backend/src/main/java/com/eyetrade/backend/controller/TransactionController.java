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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Created by Emir Gökdemir
 * on 24 Kas 2019
 */
@RestController
@Api(value = "Trading Accounts and Transactions", tags = {"Trading Accounts and Transactions"})
public class TransactionController {

    @Autowired
    private TransactionService service;

    @Autowired
    private JwtResolver jwtResolver;

    @Autowired
    private JwtUserChecker jwtUserChecker;

    @ApiOperation(value = "A trader user can create trading account.", response = UserAccountResource.class)
    @GetMapping("/create_trading_account")
    public ResponseEntity createTradingAccount(@RequestHeader("Authorization") String token) {
        try {
            UUID userId = jwtUserChecker.resolveTraderToken(token);
            return ResponseEntity.ok(service.createTradingAccount(userId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @ApiOperation(value = "A trader user can access his/her trading account.", response = UserAccountResource.class)
    @GetMapping("/get_trading_account")
    public ResponseEntity getTradingAccount(@RequestHeader("Authorization") String token) {
        try {
            UUID userId = jwtResolver.getIdFromToken(token);
            return ResponseEntity.ok(service.getUserTradingAccount(userId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @ApiOperation(value = "A user can buy fund with dto.", response = BuyTransactionResource.class)
    @PostMapping("/buy_transaction")
    public ResponseEntity buyFund(@RequestHeader("Authorization") String token,
                                  @RequestBody BuyTransactionDto dto) {
        try {
            BuyTransactionResource resource = service.buyFund(dto, jwtResolver.getIdFromToken(token));
            return ResponseEntity.ok(resource);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation(value = "A user can sell fund with dto.", response = SellTransactionResource.class)
    @PostMapping("/sell_transaction")
    public ResponseEntity sellFund(@RequestHeader("Authorization") String token,
                                   @RequestBody SellTransactionDto dto) {
        try {
            UUID userId = jwtResolver.getIdFromToken(token);
            SellTransactionResource resource = service.sellfund(dto, userId);
            return ResponseEntity.ok(resource);
        } catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation(value = "A user can buy fund with dto.", response = ExchangeTransactionResource.class)
    @PostMapping("/exchange_transaction")
    public ResponseEntity exchangeFunds(@RequestHeader("Authorization") String token,
                                        @RequestBody ExchangeTransactionDto dto) {


        try {
            UUID userId = jwtResolver.getIdFromToken(token);
            ExchangeTransactionResource resource = service.exchangeFunds(dto, userId);
            return ResponseEntity.ok(resource);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation(value = "Update buy sell orders method. It is created for only testing for development progress.", response = void.class)
    @GetMapping("/update_orders")
    public void updateOrders() {
        service.checkBuySellOrder();
    }
}
