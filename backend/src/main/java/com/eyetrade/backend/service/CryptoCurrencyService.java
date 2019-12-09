package com.eyetrade.backend.service;

import com.eyetrade.backend.constants.CryptoCurrencyType;
import com.eyetrade.backend.constants.CurrencyConstants;
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
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class CryptoCurrencyService {

    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired private CryptoCurrencyRepository cryptoCurrencyRepository;

    private Map<?, ?> getExchangeRate(CryptoCurrencyType toCurrency) throws IOException, URISyntaxException {
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
            return data;
        }
    }

}
