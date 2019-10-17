package com.eyetrade.backend.service;

import com.eyetrade.backend.constants.CurrencyConstants;
import com.eyetrade.backend.model.entity.CurrencyRecord;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class CurrencyRecordService {

    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public CurrencyRecord getCurrencyRecord() throws IOException {
        Map<String, Number> rates = getCurrencyRates();
        CurrencyRecord record = new CurrencyRecord();
        record.setTimestamp(new Date().getTime());
        record.setDollar(rates.get("USD").doubleValue());
        record.setEuro(rates.get("EUR").doubleValue());
        record.setTurkish_lira(rates.get("TRY").doubleValue());
        record.setSterling(rates.get("GBP").doubleValue());
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

}
