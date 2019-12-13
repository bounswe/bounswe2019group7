package com.eyetrade.backend.service;

import com.eyetrade.backend.constants.CryptoCurrencyType;
import com.eyetrade.backend.constants.CurrencyConstants;
import com.eyetrade.backend.constants.GeneralConstants;
import com.eyetrade.backend.model.dto.currency.CryptoCurrencyConverterDto;
import com.eyetrade.backend.model.dto.currency.CryptoCurrencyConverterIntervalDto;
import com.eyetrade.backend.model.dto.currency.CryptoCurrencyConverterLastDaysDto;
import com.eyetrade.backend.model.entity.CryptoCurrencyRecord;
import com.eyetrade.backend.model.resource.currency.CryptoCurrencyConverterResource;
import com.eyetrade.backend.model.resource.currency.CryptoCurrencyIntervalResource;
import com.eyetrade.backend.model.resource.currency.CryptoCurrencyLastDaysResource;
import com.eyetrade.backend.repository.CryptoCurrencyRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import static com.eyetrade.backend.constants.GeneralConstants.DB_DATE_TIME_FORMAT;
import static com.eyetrade.backend.utils.DateUtils.dateTimeFormatter;
import static com.eyetrade.backend.utils.DateUtils.getDateXDaysAgoWithFormat;

@Service
public class CryptoCurrencyService {

    private final RequestConfig httpConfig = RequestConfig.custom()
            .setConnectTimeout(GeneralConstants.API_REQUEST_LONG_TIMEOUT)
            .setConnectionRequestTimeout(GeneralConstants.API_REQUEST_LONG_TIMEOUT)
            .setSocketTimeout(GeneralConstants.API_REQUEST_LONG_TIMEOUT)
            .build();
    private final CloseableHttpClient httpClient = HttpClientBuilder.create()
            .setDefaultRequestConfig(httpConfig)
            .build();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired private CryptoCurrencyRepository cryptoCurrencyRepository;

    public CryptoCurrencyIntervalResource getRatesBetweenDates(CryptoCurrencyConverterIntervalDto dto) {
        List<CryptoCurrencyRecord> currencyRecords = cryptoCurrencyRepository.findCryptoCurrencyRecordsByDateBetweenOrderByDate(dto.getStartDate(), dto.getEndDate());
        List<CryptoCurrencyConverterResource> resources = new ArrayList<>();
        for(CryptoCurrencyRecord record : currencyRecords){
            CryptoCurrencyConverterResource resource = generateResource(record, dto.getSource(), dto.getTarget(), dto.getAmount());
            resources.add(resource);
        }
        return new CryptoCurrencyIntervalResource(resources, dto.getStartDate(), dto.getEndDate());
    }

    public CryptoCurrencyLastDaysResource getRatesLastDays(CryptoCurrencyConverterLastDaysDto dto) {
        String startDate = getDateXDaysAgoWithFormat(dto.getDayCount(), DB_DATE_TIME_FORMAT);
        List<CryptoCurrencyRecord> currencyRecords = cryptoCurrencyRepository.findCryptoCurrencyRecordsByDateAfterOrderByDate(startDate);
        List<CryptoCurrencyConverterResource> resources = new ArrayList<>();
        for(CryptoCurrencyRecord record : currencyRecords){
            CryptoCurrencyConverterResource resource = generateResource(record, dto.getSource(), dto.getTarget(), dto.getAmount());
            resources.add(resource);
        }
        return new CryptoCurrencyLastDaysResource(resources, startDate, dto.getDayCount());
    }

    public CryptoCurrencyConverterResource convertCryptoCurrencies(CryptoCurrencyConverterDto dto) throws IOException, URISyntaxException {
        CryptoCurrencyRecord lastRecord = getLastCryptoCurrencyRecord();
        return generateResource(lastRecord, dto.getSource(), dto.getTarget(), dto.getAmount());
    }

    private CryptoCurrencyConverterResource generateResource(CryptoCurrencyRecord record,
                                                                    CryptoCurrencyType source,
                                                                    CryptoCurrencyType target,
                                                                    Double amount){
        CryptoCurrencyConverterResource resource = new CryptoCurrencyConverterResource();
        resource.setSourceType(source);
        resource.setTargetType(target);
        resource.setDate(record.getDate());
        resource.setRate(findRate(record, target) / findRate(record, source));
        resource.setAmount(amount * resource.getRate());
        return resource;
    }

    private CryptoCurrencyRecord getLastCryptoCurrencyRecord() throws IOException, URISyntaxException {
        CryptoCurrencyRecord record = cryptoCurrencyRepository.findLastRecord();
        return record == null ? getNewCryptoCurrencyRecord() : record;
    }

    @Transactional
    @Scheduled(cron = "0 0 6 * * ?") //scheduled every day at 6 am.
    private CryptoCurrencyRecord getNewCryptoCurrencyRecord() throws IOException, URISyntaxException {
        CryptoCurrencyRecord record = new CryptoCurrencyRecord();
        // because of the api I have to make separate calls for each of the currency types
        record.setBitcoin(getExchangeRate(CryptoCurrencyType.BTC));
        record.setEthereum(getExchangeRate(CryptoCurrencyType.ETH));
        record.setLitecoin(getExchangeRate(CryptoCurrencyType.LTC));
        record.setMonero(getExchangeRate(CryptoCurrencyType.XMR));
        record.setRipple(getExchangeRate(CryptoCurrencyType.XRP));
        record.setDate(dateTimeFormatter(new Date(), DB_DATE_TIME_FORMAT));
        cryptoCurrencyRepository.save(record);
        return record;
    }

    private Double getExchangeRate(CryptoCurrencyType toCurrency) throws IOException, URISyntaxException {
        // TODO: We should decide what to do with that unstable api
        // give 4 chance to the api
        for(int i = 0; i < 3; i++){
            try{
                return getExchangeRateFromApi(toCurrency);
            }
            catch (Exception e) {
            }
        }
        return getExchangeRateFromApi(toCurrency);
    }

    private Double getExchangeRateFromApi(CryptoCurrencyType toCurrency) throws URISyntaxException, IOException {
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
            String rateInStr = ((Map<String, String>) data.get("Realtime Currency Exchange Rate")).get("5. Exchange Rate");
            return Double.parseDouble(rateInStr);
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
        else{ // XRP
            return record.getRipple();
        }
    }

}
