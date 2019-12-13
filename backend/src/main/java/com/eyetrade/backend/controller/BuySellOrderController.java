package com.eyetrade.backend.controller;

import com.eyetrade.backend.model.dto.BuySellOrderDto;
import com.eyetrade.backend.model.resource.transaction.BuySellOrderResource;
import com.eyetrade.backend.security.JwtResolver;
import com.eyetrade.backend.service.BuySellOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Emir Gökdemir
 * on 13 Ara 2019
 */

@Api(value = "Buy Sell Order", tags = {"Buy Sell Order Operations"})
@RestController
@RequestMapping("/buy_sell_order")
public class BuySellOrderController {

    @Autowired
    private JwtResolver jwtResolver;

    @Autowired
    private BuySellOrderService service;


    @ApiOperation(value = "User can add buy or sell order of currency for a specific rate", response = BuySellOrderResource.class)
    @PostMapping("/add")
    public ResponseEntity<BuySellOrderResource> addAnnotation(@RequestHeader("Authorization") String token, @RequestBody BuySellOrderDto dto) {
        return ResponseEntity.ok(service.addBuySellOrder(dto, jwtResolver.getIdFromToken(token)));
    }
}
