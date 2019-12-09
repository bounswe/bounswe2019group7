package com.eyetrade.backend.controller;

import com.eyetrade.backend.model.dto.currency.CryptoCurrencyConverterDto;
import com.eyetrade.backend.model.dto.currency.CryptoCurrencyConverterIntervalDto;
import com.eyetrade.backend.model.dto.currency.CryptoCurrencyConverterLastDaysDto;
import com.eyetrade.backend.model.resource.currency.CryptoCurrencyConverterResource;
import com.eyetrade.backend.model.resource.currency.CryptoCurrencyIntervalResource;
import com.eyetrade.backend.model.resource.currency.CryptoCurrencyLastDaysResource;
import com.eyetrade.backend.service.CryptoCurrencyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;

@Api(value = "Crypto Currencies", tags = {"Crypto Currencies"})
@RestController
@RequestMapping("/crypto_currency")
public class CryptoCurrencyController {

    @Autowired
    private CryptoCurrencyService cryptoCurrencyService;

    @ApiOperation(value = "Takes the source and target crypto currency with the amount of source; then returns the conversion", response = CryptoCurrencyConverterResource.class)
    @GetMapping("/convert")
    public ResponseEntity<?> convert(CryptoCurrencyConverterDto dto){
        try{
            CryptoCurrencyConverterResource resource = cryptoCurrencyService.convertCryptoCurrencies(dto);
            return ResponseEntity.ok(resource);
        }
        catch (URISyntaxException | IOException e) {
            return (ResponseEntity<?>) ResponseEntity.badRequest();
        }

    }

    @ApiOperation(value = "Takes the crypto currency rates between two dates, target currency, amount of source currency; then returns the list of exchange rates and amounts", response = CryptoCurrencyIntervalResource.class)
    @GetMapping("/take-rates-between-dates")
    public ResponseEntity<CryptoCurrencyIntervalResource> convertInInterval(CryptoCurrencyConverterIntervalDto dto) {
        CryptoCurrencyIntervalResource resource = cryptoCurrencyService.getRatesBetweenDates(dto);
        return ResponseEntity.ok(resource);

    }

    @ApiOperation(value = "Takes the crypto currency rates last X days, with target currency, amount of source currency; then returns the list of exchange rates and amounts", response = CryptoCurrencyLastDaysResource.class)
    @GetMapping("/take-rates-last-days")
    public ResponseEntity<CryptoCurrencyLastDaysResource> convertLastDays(CryptoCurrencyConverterLastDaysDto dto) {
        CryptoCurrencyLastDaysResource resource = cryptoCurrencyService.getRatesLastDays(dto);
        return ResponseEntity.ok(resource);
    }

}
