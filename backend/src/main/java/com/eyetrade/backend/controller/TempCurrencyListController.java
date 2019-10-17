package com.eyetrade.backend.controller;

import com.eyetrade.backend.service.CurrencyRecordService;
import com.eyetrade.backend.model.entity.CurrencyRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

// TODO: Delete this controller later

@RestController
@RequestMapping("/currency")
public class TempCurrencyListController {

    @Autowired
    private CurrencyRecordService currencyRecordService;

    @GetMapping("/list")
    public ResponseEntity<CurrencyRecord> list() throws IOException {
        CurrencyRecord record = currencyRecordService.getCurrencyRecord();
        return ResponseEntity.ok(record);
    }

}
