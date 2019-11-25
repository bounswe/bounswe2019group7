package com.eyetrade.backend.controller;

import com.eyetrade.backend.mapper.CurrencyRecordMapper;
import com.eyetrade.backend.model.dto.currency.CurrencyConverterDto;
import com.eyetrade.backend.model.dto.currency.CurrencyConverterIntervalDto;
import com.eyetrade.backend.model.dto.currency.CurrencyConverterLastDaysDto;
import com.eyetrade.backend.model.resource.currency.CurrencyConverterResource;
import com.eyetrade.backend.model.resource.currency.CurrencyIntervalResource;
import com.eyetrade.backend.model.resource.currency.CurrencyLastDaysResource;
import com.eyetrade.backend.model.resource.currency.CurrencyRecordResource;
import com.eyetrade.backend.service.CurrencyRateService;
import com.eyetrade.backend.service.CurrencyRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Created by Emir Gökdemir
 * on 17 Eki 2019
 */
@Api(value = "Currencies", tags = {"Currencies"})
@RestController
@RequestMapping("/currency")
public class CurrencyController {

    @Autowired
    private CurrencyRateService currencyRateService;

    @Autowired
    private CurrencyRecordService recordService;

    @Autowired
    private CurrencyRecordMapper recordMapper;

    @ApiOperation(value = "Takes the source currency, target currency, amount of source currency; then converts the currency amount", response = CurrencyConverterResource.class)
    @GetMapping("/convert")
    public ResponseEntity<CurrencyConverterResource> convertCurrency(CurrencyConverterDto converterDto){
        CurrencyConverterResource result = currencyRateService.convertCurrency(converterDto);
        return ResponseEntity.ok(result);
    }

    @ApiOperation(value = "Takes the currency rates between two dates, target currency, amount of source currency; then it returns the list of exchange rate and amounts", response = CurrencyIntervalResource.class)
    @GetMapping("/take-rates-between-dates")
    public ResponseEntity<CurrencyIntervalResource> convertCurrency(CurrencyConverterIntervalDto dto) {
        return ResponseEntity.ok(currencyRateService.findRateBetweenDates(dto));
    }

    @ApiOperation(value = "Takes the currency rates last X days, with target currency, amount of source currency; then it returns the list of exchange rate and amounts", response = CurrencyLastDaysResource.class)
    @GetMapping("/take-rates-last-days")
    public ResponseEntity<CurrencyLastDaysResource> convertCurrency(CurrencyConverterLastDaysDto dto) {
        return ResponseEntity.ok(currencyRateService.findRateLastDays(dto));
    }

    @ApiOperation(value = "Updates the currency rates, and saves to DB and returns the last record. It is only for us not for customers ", response = CurrencyRecordResource.class)
    @GetMapping("/update-currency-rates")
    public ResponseEntity<CurrencyRecordResource> updateRecord() throws IOException {
        return ResponseEntity.ok(recordMapper.entityToResource(recordService.getCurrencyRecord()));
    }
}
