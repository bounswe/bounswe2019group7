package com.eyetrade.backend.service;

import com.eyetrade.backend.constants.CryptoCurrencyType;
import com.eyetrade.backend.constants.CurrencyConstants;
import com.eyetrade.backend.model.dto.currency.CryptoCurrencyConverterDto;
import com.eyetrade.backend.model.entity.CryptoCurrencyRecord;
import com.eyetrade.backend.model.resource.currency.CryptoCurrencyConverterResource;
import com.eyetrade.backend.repository.CryptoCurrencyRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.eyetrade.backend.constants.GeneralConstants.DB_DATE_TIME_FORMAT;
import static com.eyetrade.backend.utils.DateUtils.dateTimeFormatter;

@Service
public class CryptoCurrencyService {

    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired private CryptoCurrencyRepository cryptoCurrencyRepository;

    public CryptoCurrencyConverterResource convertCryptoCurrencies(CryptoCurrencyConverterDto dto) throws IOException, URISyntaxException {
        CryptoCurrencyConverterResource resource = new CryptoCurrencyConverterResource();
        CryptoCurrencyRecord lastRecord = getLastCryptoCurrencyRecord();
        resource.setSourceType(dto.getSource());
        resource.setTargetType(dto.getTarget());
        resource.setDate(lastRecord.getDate());
        resource.setRate(findRate(lastRecord, dto.getTarget()) / findRate(lastRecord, dto.getSource()));
        resource.setAmount(dto.getAmount() * resource.getRate());
        return resource;
    }

    private CryptoCurrencyRecord getLastCryptoCurrencyRecord() throws IOException, URISyntaxException {
        CryptoCurrencyRecord record = cryptoCurrencyRepository.findLastRecord();
        return record == null ? getNewCryptoCurrencyRecord() : record;
    }

    @Transactional
    @Scheduled(cron = "0 0 6 * * ?") //scheduled of every day at 6 am.
    private CryptoCurrencyRecord getNewCryptoCurrencyRecord() throws IOException, URISyntaxException {
        CryptoCurrencyRecord record = new CryptoCurrencyRecord();
        // because of the api I have to make separate calls for each of the currency types
        record.setBitcoin(getExchangeRate(CryptoCurrencyType.BTC));
        record.setEthereum(getExchangeRate(CryptoCurrencyType.ETH));
        record.setLitecoin(getExchangeRate(CryptoCurrencyType.LTC));
        record.setMonero(getExchangeRate(CryptoCurrencyType.XMR));
        record.setRipple(getExchangeRate(CryptoCurrencyType.XRP));
        record.setZcash(getExchangeRate(CryptoCurrencyType.ZEC));
        record.setDate(dateTimeFormatter(new Date(), DB_DATE_TIME_FORMAT));
        cryptoCurrencyRepository.save(record);
        return record;
    }

    private Double getExchangeRate(CryptoCurrencyType toCurrency) throws IOException, URISyntaxException {
        URI apiUri = new URIBuilder()
                .setScheme("https")
                .setHost(CurrencyConstants.CRYPTO_CURRENCY_API_HOST)
                .setPath(CurrencyConstants.CRYPTO_CURRENCY_API_PATH)
                .setParameter("function", CurrencyConstants.CRYPTO_CURRENCY_API_EXCHANGE_FUNCTION)
                .setParameter("from_currency", CurrencyConstants.CRYPTO_CURRENCY_API_BASE)
                .setParameter("to_currency", toCurrency.toString())
                .setParameter("apikey", CurrencyConstants.CRYPTO_CURRENCY_API_KEY)
                .build();
        HttpUriRequest request = new HttpGet(apiUri);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity entity = response.getEntity();
            InputStream content = entity.getContent();
            Map<?, ?> data = objectMapper.readValue(content, LinkedHashMap.class);
            return ((Map<String, Number>) data.get("Realtime Currency Exchange Rate"))
                    .get("5. Exchange Rate")
                    .doubleValue();
        }
    }

    // a mapper method
    private Double findRate(CryptoCurrencyRecord record, CryptoCurrencyType type){
        if(type == CryptoCurrencyType.BTC){
            return record.getBitcoin();
        }
        else if(type == CryptoCurrencyType.ETH){
            return record.getEthereum();
        }
        else if(type == CryptoCurrencyType.LTC){
            return record.getLitecoin();
        }
        else if(type == CryptoCurrencyType.XMR){
            return record.getMonero();
        }
        else if(type == CryptoCurrencyType.XRP){
            return record.getRipple();
        }
        else{
            return record.getZcash();
        }
    }

}
