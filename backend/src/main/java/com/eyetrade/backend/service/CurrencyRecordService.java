package com.eyetrade.backend.service;

import com.eyetrade.backend.constants.CurrencyConstants;
import com.eyetrade.backend.constants.CurrencyType;
import com.eyetrade.backend.model.entity.CurrencyRecord;
import com.eyetrade.backend.repository.CurrencyRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.eyetrade.backend.constants.CurrencyConstants.CURRENCY_EXPIRE_TIME;
import static com.eyetrade.backend.constants.ErrorConstants.CURRENCIES_COULD_NOT_BE_UPDATED;
import static com.eyetrade.backend.constants.GeneralConstants.DB_DATE_TIME_FORMAT;
import static com.eyetrade.backend.utils.DateUtils.dateTimeFormatter;

@Service
public class CurrencyRecordService {

    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private CurrencyRepository currencyRepository;

    @Transactional
    @Scheduled(cron = "0 0 4 * * ?") //scheduled of every day at 4 am.
    public CurrencyRecord getCurrencyRecord() throws IOException {
        Map<String, Number> rates = getCurrencyRates();
        CurrencyRecord record = new CurrencyRecord();
        record.setDate(dateTimeFormatter(new Date(),DB_DATE_TIME_FORMAT));
        record.setDollarRate(rates.get(CurrencyType.USD.toString()).doubleValue());
        record.setEuroRate(rates.get(CurrencyType.EUR.toString()).doubleValue());
        record.setTurkishLiraRate(rates.get(CurrencyType.TRY.toString()).doubleValue());
        record.setSterlingRate(rates.get(CurrencyType.GBP.toString()).doubleValue());
        currencyRepository.save(record);
        return record;
    }

    private Map<String, Number> getCurrencyRates() throws IOException {
        Map<?, ?> data = getDataFromApi();
        return (Map<String, Number>) data.get("rates");
    }

    private Map<?, ?> getDataFromApi() throws IOException {
        HttpUriRequest request = new HttpGet(CurrencyConstants.CURRENCY_ENDPOINT);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity entity = response.getEntity();
            InputStream content = entity.getContent();
            Map<?, ?> data = objectMapper.readValue(content, LinkedHashMap.class);
            return data;
        }
    }


    public CurrencyRecord updateIfCurrenciesExpiredAndGetLastRecord(){
        CurrencyRecord record= currencyRepository.findLastRecord();
        try{
            if(record==null || checkExpired((new SimpleDateFormat(DB_DATE_TIME_FORMAT).parse(record.getDate())).getTime())){
                try {
                    record=getCurrencyRecord();
                } catch (IOException e) {
                    throw new RuntimeException(CURRENCIES_COULD_NOT_BE_UPDATED);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return record;
    }

    private boolean checkExpired(Long lastUpdateTime){
        return (new Date().getTime()-lastUpdateTime>CURRENCY_EXPIRE_TIME);
    }


}
