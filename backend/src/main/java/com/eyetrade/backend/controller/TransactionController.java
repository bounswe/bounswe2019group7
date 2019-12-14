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

import static com.eyetrade.backend.constants.ErrorConstants.FUND_IS_NOT_ENOUGH_FOR_THIS_OPERATION;

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
    public ResponseEntity<UserAccountResource> createTradingAccount(@RequestHeader("Authorization") String token) throws IllegalAccessException {
        UUID userId = jwtUserChecker.resolveTraderToken(token);
        return ResponseEntity.ok(service.createTradingAccount(userId));
    }

    @ApiOperation(value = "A trader user can access his/her trading account.", response = UserAccountResource.class)
    @GetMapping("/get_trading_account")
    public ResponseEntity<UserAccountResource> getTradingAccount(@RequestHeader("Authorization") String token) {
        UUID userId = jwtResolver.getIdFromToken(token);
        return ResponseEntity.ok(service.getUserTradingAccount(userId));
    }

    @ApiOperation(value = "A user can buy fund with dto.", response = BuyTransactionResource.class)
    @PostMapping("/buy_transaction")
    public ResponseEntity buyFund(@RequestHeader("Authorization") String token,
                                  @RequestBody BuyTransactionDto dto) {
        BuyTransactionResource resource = service.buyFund(dto, jwtResolver.getIdFromToken(token));
        if (resource.getIsSuccessful()) {
            return ResponseEntity.ok(resource);
        } else {
            return ResponseEntity.badRequest().body(FUND_IS_NOT_ENOUGH_FOR_THIS_OPERATION);
        }
    }

    @ApiOperation(value = "A user can sell fund with dto.", response = SellTransactionResource.class)
    @PostMapping("/sell_transaction")
    public ResponseEntity sellFund(@RequestHeader("Authorization") String token,
                                                       @RequestBody SellTransactionDto dto){
        UUID userId=jwtResolver.getIdFromToken(token);
        SellTransactionResource resource=service.sellfund(dto,userId);
        if (resource.getIsSuccessful()) {
            return ResponseEntity.ok(resource);
        } else {
            return ResponseEntity.badRequest().body(FUND_IS_NOT_ENOUGH_FOR_THIS_OPERATION);
        }
    }

    @ApiOperation(value = "A user can buy fund with dto.", response = ExchangeTransactionResource.class)
    @PostMapping("/exchange_transaction")
    public ResponseEntity exchangeFunds(@RequestHeader("Authorization") String token,
                                        @RequestBody ExchangeTransactionDto dto) {
        UUID userId = jwtResolver.getIdFromToken(token);
        ExchangeTransactionResource resource = service.exchangeFunds(dto, userId);

        if (resource.getIsSuccessful()) {
            return ResponseEntity.ok(resource);
        } else {
            return ResponseEntity.badRequest().body(FUND_IS_NOT_ENOUGH_FOR_THIS_OPERATION);
        }
    }

    @ApiOperation(value = "Update buy sell orders method. It is created for only testing for development progress.", response = void.class)
    @GetMapping("/update_orders")
    public void updateOrders() {
        service.checkBuySellOrder();
    }
}
