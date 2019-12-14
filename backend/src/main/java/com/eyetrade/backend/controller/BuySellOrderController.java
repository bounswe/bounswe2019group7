package com.eyetrade.backend.controller;

import com.eyetrade.backend.model.dto.BuySellOrderDto;
import com.eyetrade.backend.model.resource.transaction.BuySellOrderResource;
import com.eyetrade.backend.security.JwtResolver;
import com.eyetrade.backend.service.BuySellOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Created by Emir Gökdemir
 * on 13 Ara 2019
 */

@Api(value = "Buy Sell Order", tags = {"Buy Sell Order Operations"})
@RestController
@RequestMapping("/buy_sell_order")
public class BuySellOrderController  {

    @Autowired
    private JwtResolver jwtResolver;

    @Autowired
    private BuySellOrderService service;

    @ApiOperation(value = "User can add buy or sell order of currency for a specific rate \n " +
            "   For buying with fund: soldType and soldAmount should be left as null \n" +
            "   For selling for fund: boughtType and boughtAmount should be left as null \n" +
            "   For exchange operation: both type should be filled.Rate should use as exchange rate (boughtTypeRate/SoldTypeRate)." +
            " Amount should use as soldAmount.    For example: For selling 5 dollars and buying 4 euros your exchange rate should include 1.25" +
            "and your amount should be 5", response = BuySellOrderResource.class)
    @PostMapping("/add")
    public ResponseEntity addBuySellOrder(@RequestHeader("Authorization") String token, @RequestBody BuySellOrderDto dto) {
        try {
            return ResponseEntity.ok(service.addBuySellOrder(dto, jwtResolver.getIdFromToken(token)));
        }catch (ArithmeticException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation(value = "User can see all buy or sell order of himself/herself", response = BuySellOrderResource.class,responseContainer = "List")
    @GetMapping("/get_all_self")
    public ResponseEntity<List<BuySellOrderResource>> getAllBuySellOrder(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(service.getSelfOrders(jwtResolver.getIdFromToken(token)));
    }

    @ApiOperation(value = "User can update buy or sell order of currency for a specific rate", response = BuySellOrderResource.class)
    @PutMapping("/update")
    public ResponseEntity updateBuySellOrder(@RequestHeader("Authorization") String token, @RequestBody BuySellOrderDto dto, @RequestParam UUID buySellOrderId) {
        try {
            return ResponseEntity.ok(service.updateBuySellOrder(dto,buySellOrderId, jwtResolver.getIdFromToken(token)));
        } catch (IllegalAccessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (ArithmeticException e){
           return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation(value = "User can delete buy or sell order of currency", response = ResponseEntity.class)
    @DeleteMapping("/delete")
    public ResponseEntity deleteBuySellOrder(@RequestHeader("Authorization") String token, @RequestParam UUID buySellOrderId) {
        try {
            service.deleteBuySellOrder(buySellOrderId, jwtResolver.getIdFromToken(token));
        } catch (IllegalAccessException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("succesfully deleted!");
    }

}
