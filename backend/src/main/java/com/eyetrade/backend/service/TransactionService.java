package com.eyetrade.backend.service;

import com.eyetrade.backend.model.dto.currency.CurrencyConverterDto;
import com.eyetrade.backend.model.dto.transaction.BuyTransactionDto;
import com.eyetrade.backend.model.dto.transaction.ExchangeTransactionDto;
import com.eyetrade.backend.model.entity.User;
import com.eyetrade.backend.model.entity.UserTradingAccount;
import com.eyetrade.backend.model.resource.currency.CurrencyConverterResource;
import com.eyetrade.backend.model.resource.transaction.BuyTransactionResource;
import com.eyetrade.backend.model.resource.transaction.ExchangeTransactionResource;
import com.eyetrade.backend.repository.UserRepository;
import com.eyetrade.backend.repository.UserTradingAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

/**
 * Created by Emir Gökdemir
 * on 24 Kas 2019
 */
@Service
public class TransactionService {

    @Autowired
    private UserTradingAccountRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CurrencyRateService currencyRateService;

    @Transactional
    public String createTradingAccount(UUID userId) {
        User user = userRepository.findById(userId);
        UserTradingAccount account = new UserTradingAccount();
        account.setUserId(user);
        repository.save(account);
        return "Account is created!";
    }


    @Transactional
    public BuyTransactionResource buyFund(BuyTransactionDto transactionDto, UUID userId) {
        User user = userRepository.findById(userId);
        UserTradingAccount account = repository.findUserTradingAccountByUserId(user);
        BuyTransactionResource resource = new BuyTransactionResource(transactionDto.getBoughtTypeCurrency());
        switch (transactionDto.getBoughtTypeCurrency()) {
            case EUR:
                resource.setBoughtTypeInitialAmount(account.getEUR());
                account.setEUR(account.getEUR() + transactionDto.getAmount());
                break;
            case TRY:
                resource.setBoughtTypeInitialAmount(account.getTRY());
                account.setTRY(account.getTRY() + transactionDto.getAmount());
                break;
            case USD:
                resource.setBoughtTypeInitialAmount(account.getUSD());
                account.setUSD(account.getUSD() + transactionDto.getAmount());
                break;
            case CNY:
                resource.setBoughtTypeInitialAmount(account.getCNY());
                account.setCNY(account.getCNY() + transactionDto.getAmount());
                break;
            case GBP:
                resource.setBoughtTypeInitialAmount(account.getGBP());
                account.setGBP(account.getGBP() + transactionDto.getAmount());
                break;
            case JPY:
                resource.setBoughtTypeInitialAmount(account.getJPY());
                account.setJPY(account.getJPY() + transactionDto.getAmount());
                break;
        }
        resource.setBoughtTypeLastAmount(resource.getBoughtTypeInitialAmount() + transactionDto.getAmount());
        repository.saveAndFlush(account);
        return resource;
    }

    @Transactional
    public ExchangeTransactionResource exchangeFunds(ExchangeTransactionDto transactionDto, UUID userId) {

        ExchangeTransactionResource resource= new ExchangeTransactionResource();
        UserTradingAccount account= repository.findUserTradingAccountByUserId(userRepository.findById(userId));
        //Convert Currency
        CurrencyConverterDto converterDto = new CurrencyConverterDto();
        converterDto.setInputCurrencyType(transactionDto.getSoldTypeCurrency());
        converterDto.setOutputCurrencyType(transactionDto.getBoughtTypeCurrency());
        converterDto.setAmount(transactionDto.getAmountOfSold());
        CurrencyConverterResource converterResource = currencyRateService.convertCurrency(converterDto);

        resource.setRate(converterResource.getRate());

        //Decrease fund which is sold
        BuyTransactionResource buyTransactionResource= buyFund(new BuyTransactionDto(converterDto.getInputCurrencyType(),-transactionDto.getAmountOfSold()),userId);
        resource.setSoldTypeInitialAmount(buyTransactionResource.getBoughtTypeInitialAmount());
        resource.setSoldTypeLastAmount(buyTransactionResource.getBoughtTypeLastAmount());
        resource.setSoldTypeCurrency(buyTransactionResource.getBoughtTypeCurrency());

        //Increase Fund which is bought
        buyTransactionResource=buyFund(new BuyTransactionDto(converterDto.getOutputCurrencyType(),converterResource.getAmount()),userId);
        resource.setBoughtTypeInitialAmount(buyTransactionResource.getBoughtTypeInitialAmount());
        resource.setBoughtTypeLastAmount(buyTransactionResource.getBoughtTypeLastAmount());
        resource.setBoughtTypeCurrency(buyTransactionResource.getBoughtTypeCurrency());
        return resource;
    }
}
