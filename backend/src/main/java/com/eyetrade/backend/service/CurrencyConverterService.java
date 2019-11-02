package com.eyetrade.backend.service;

import com.eyetrade.backend.constants.CurrencyType;
import com.eyetrade.backend.model.dto.CurrencyConverterDto;
import com.eyetrade.backend.model.entity.CurrencyRecord;
import com.eyetrade.backend.model.resource.CurrencyConverterResource;
import com.eyetrade.backend.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Emir Gökdemir
 * on 17 Eki 2019
 */
@Service
public class CurrencyConverterService {

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private CurrencyRecordService currencyRecordService;

    public CurrencyConverterResource convertCurrency (CurrencyConverterDto converterDto){
        CurrencyRecord record= currencyRecordService.updateIfCurrenciesExpiredAndGetLastRecord();
        CurrencyConverterResource resource= new CurrencyConverterResource();
        resource.setRate(
                findRate(converterDto.getOutputCurrencyType(),record)/findRate(converterDto.getInputCurrencyType(),record));
        resource.setAmount(resource.getRate()*converterDto.getAmount());
        return resource;
    }

    public Double findRate(CurrencyType type, CurrencyRecord record) {
        switch (type) {
            case TRY:
                return record.getTurkishLiraRate();
            case EUR:
                return record.getEuroRate();
            case GBP:
                return record.getSterlingRate();
            case USD:
                return record.getDollarRate();
        }
        return 0.00;
    }
}
