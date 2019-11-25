package com.eyetrade.backend.service;

import com.eyetrade.backend.constants.CurrencyType;
import com.eyetrade.backend.model.dto.currency.CurrencyConverterDto;
import com.eyetrade.backend.model.dto.currency.CurrencyConverterIntervalDto;
import com.eyetrade.backend.model.dto.currency.CurrencyConverterLastDaysDto;
import com.eyetrade.backend.model.entity.CurrencyRecord;
import com.eyetrade.backend.model.resource.currency.CurrencyConverterResource;
import com.eyetrade.backend.model.resource.currency.CurrencyIntervalResource;
import com.eyetrade.backend.model.resource.currency.CurrencyLastDaysResource;
import com.eyetrade.backend.repository.CurrencyRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.eyetrade.backend.constants.ErrorConstants.NO_SUCH_CURRENCY_TYPES;
import static com.eyetrade.backend.constants.GeneralConstants.DB_DATE_TIME_FORMAT;
import static com.eyetrade.backend.utils.DateUtils.getDateXDaysAgoWithFormat;

/**
 * Created by Emir Gökdemir
 * on 17 Eki 2019
 */
@Service
public class CurrencyRateService {

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private CurrencyRecordService currencyRecordService;

    public CurrencyConverterResource convertCurrency(CurrencyConverterDto converterDto) {
        CurrencyRecord record = currencyRecordService.updateIfCurrenciesExpiredAndGetLastRecord();
        CurrencyConverterResource resource = new CurrencyConverterResource();
        resource.setRate(
                findRate(converterDto.getOutputCurrencyType(), record) / findRate(converterDto.getInputCurrencyType(), record));
        resource.setAmount(resource.getRate() * converterDto.getAmount());
        resource.setInputCurrencyType(converterDto.getInputCurrencyType());
        resource.setOutputCurrencyType(converterDto.getOutputCurrencyType());
        return resource;
    }

    public CurrencyRecord findLastRecord(){
        return currencyRecordService.updateIfCurrenciesExpiredAndGetLastRecord();
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
            case JPY:
                return record.getJapanRate();
            case CNY:
                return record.getChinaRate();
            default:
                throw new RuntimeException(NO_SUCH_CURRENCY_TYPES);
        }
    }

    public CurrencyIntervalResource findRateBetweenDates(CurrencyConverterIntervalDto dto) {
        List<CurrencyRecord> currencyRecords = currencyRepository.findCurrencyRecordsByDateBetweenOrderByDate(dto.getStartDate(), dto.getEndDate());
        List<CurrencyConverterResource> resources = mapListOfCurrencyRecords(currencyRecords, dto.getTargetCurrencyType(), dto.getSourceCurrencyType(), dto.getAmount());
        return new CurrencyIntervalResource(resources, dto.getStartDate(), dto.getEndDate());
    }

    public CurrencyLastDaysResource findRateLastDays(CurrencyConverterLastDaysDto dto) {
        String startDate=getDateXDaysAgoWithFormat(dto.getLastDays(), DB_DATE_TIME_FORMAT);
        List<CurrencyRecord> currencyRecords = currencyRepository.findCurrencyRecordsByDateAfterOrderByDate(startDate);
        List<CurrencyConverterResource> resources = mapListOfCurrencyRecords(currencyRecords, dto.getTargetCurrencyType(), dto.getSourceCurrencyType(), dto.getAmount());
        return new CurrencyLastDaysResource(resources,startDate,dto.getLastDays());
    }

    private List<CurrencyConverterResource> mapListOfCurrencyRecords(List<CurrencyRecord> currencyRecords,
                                                                     CurrencyType targetCurrency, CurrencyType sourceCurrency, Float amount) {
        List<CurrencyConverterResource> resources = new ArrayList<>();
        for (CurrencyRecord record : CollectionUtils.emptyIfNull(currencyRecords)) {
            CurrencyConverterResource resource = new CurrencyConverterResource();
            resource.setDate(record.getDate());
            resource.setRate(findRate(targetCurrency, record) / findRate(sourceCurrency, record));
            resource.setAmount(resource.getRate() *amount);
            resource.setInputCurrencyType(sourceCurrency);
            resource.setOutputCurrencyType(targetCurrency);
            resources.add(resource);
        }
        return resources;
    }

}
