package com.eyetrade.backend.controller;

import com.eyetrade.backend.model.dto.CurrencyConverterDto;
import com.eyetrade.backend.model.resource.CurrencyConverterResource;
import com.eyetrade.backend.service.CurrencyConverterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Emir Gökdemir
 * on 17 Eki 2019
 */
@Api(value = "Currencies", tags = {"Operations Related With Currencies"})
@RestController
@RequestMapping("/currency")
public class CurrencyController {

    @Autowired
    CurrencyConverterService currencyConverterService;

    @ApiOperation(value = "Takes the source currency, target currency, amount of source currency; then converts the currency amount", response = CurrencyConverterResource.class)
    @GetMapping("/convert")
    public ResponseEntity<CurrencyConverterResource> convertCurrency(CurrencyConverterDto converterDto){
        CurrencyConverterResource result = currencyConverterService.convertCurrency(converterDto);
        return ResponseEntity.ok(result);
    }

}
