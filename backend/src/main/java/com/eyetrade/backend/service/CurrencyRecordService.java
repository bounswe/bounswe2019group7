package com.eyetrade.backend.service;

import com.eyetrade.backend.constants.CryptoCurrencyType;
import com.eyetrade.backend.constants.CurrencyConstants;
import com.eyetrade.backend.constants.CurrencyType;
import com.eyetrade.backend.model.entity.CurrencyRecord;
import com.eyetrade.backend.repository.CurrencyRepository;
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

    @Autowired
    private TransactionService transactionService;

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
        record.setJapanRate(rates.get(CurrencyType.JPY.toString()).doubleValue());
        record.setChinaRate(rates.get(CurrencyType.CNY.toString()).doubleValue());

        Double dollarRate=record.getDollarRate();
        try {
            record.setBitcoin(getCryptoCurrenciesExchangeRate(CryptoCurrencyType.BTC)/dollarRate);
            record.setEthereum(getCryptoCurrenciesExchangeRate(CryptoCurrencyType.ETH)/dollarRate);
            record.setLitecoin(getCryptoCurrenciesExchangeRate(CryptoCurrencyType.LTC)/dollarRate);
            record.setMonero(getCryptoCurrenciesExchangeRate(CryptoCurrencyType.XMR)/dollarRate);
            record.setRipple(getCryptoCurrenciesExchangeRate(CryptoCurrencyType.XRP)/dollarRate);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        currencyRepository.save(record);
        transactionService.checkBuySellOrder();
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
        CurrencyRecord record = currencyRepository.findLastRecord();
        try{
            if(record == null || checkExpired((new SimpleDateFormat(DB_DATE_TIME_FORMAT).parse(record.getDate())).getTime())){
                try {
                    record = getCurrencyRecord();
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

    private Double getCryptoCurrenciesExchangeRate(CryptoCurrencyType toCurrency) throws IOException, URISyntaxException {
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


}
