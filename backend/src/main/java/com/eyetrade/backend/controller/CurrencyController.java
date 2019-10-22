package com.eyetrade.backend.controller;

import com.eyetrade.backend.model.dto.CurrencyConverterDto;
import com.eyetrade.backend.model.resource.CurrencyConverterResource;
import com.eyetrade.backend.service.CurrencyConverterService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Emir Gökdemir
 * on 17 Eki 2019
 */
@Api(value = "Currencies")
@RestController
@RequestMapping("/currency")
public class CurrencyController {

    @Autowired
    CurrencyConverterService currencyConverterService;

    @GetMapping("/convert")
    public CurrencyConverterResource convertCurrency(CurrencyConverterDto converterDto){
        return currencyConverterService.convertCurrency(converterDto);
    }

}
