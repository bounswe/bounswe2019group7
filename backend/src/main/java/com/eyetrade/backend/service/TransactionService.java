package com.eyetrade.backend.service;

import com.eyetrade.backend.mapper.UserAccountMapper;
import com.eyetrade.backend.model.dto.currency.CurrencyConverterDto;
import com.eyetrade.backend.model.dto.transaction.BuyTransactionDto;
import com.eyetrade.backend.model.dto.transaction.ExchangeTransactionDto;
import com.eyetrade.backend.model.entity.User;
import com.eyetrade.backend.model.entity.UserTradingAccount;
import com.eyetrade.backend.model.resource.currency.CurrencyConverterResource;
import com.eyetrade.backend.model.resource.transaction.BuyTransactionResource;
import com.eyetrade.backend.model.resource.transaction.ExchangeTransactionResource;
import com.eyetrade.backend.model.resource.transaction.UserAccountResource;
import com.eyetrade.backend.repository.UserRepository;
import com.eyetrade.backend.repository.UserTradingAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

import static com.eyetrade.backend.constants.ErrorConstants.A_USER_CAN_HAVE_ONLY_AN_ACCOUNT;
import static com.eyetrade.backend.constants.ErrorConstants.NO_SUCH_TRADING_ACCOUNT;

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

    @Autowired
    private UserAccountMapper mapper;

    @Transactional
    public UserAccountResource createTradingAccount(UUID userId) {
        User user = userRepository.findById(userId);
        UserTradingAccount account = repository.findUserTradingAccountByUser(user);
        if (account != null) {
            throw new IllegalArgumentException(A_USER_CAN_HAVE_ONLY_AN_ACCOUNT);
        }
        account=new UserTradingAccount();
        account.setUser(user);
        return mapper.entityToResource(repository.save(account));
    }

    @Transactional
    public UserAccountResource getUserTradingAccount(UUID userId) {
        User user = userRepository.findById(userId);
        UserTradingAccount account = repository.findUserTradingAccountByUser(user);
        if (account == null) {
            throw new IllegalArgumentException(NO_SUCH_TRADING_ACCOUNT);
        }
        return mapper.entityToResource(account);
    }

    @Transactional
    public BuyTransactionResource buyFund(BuyTransactionDto transactionDto, UUID userId) {
        User user = userRepository.findById(userId);
        UserTradingAccount account = repository.findUserTradingAccountByUser(user);
        if (account == null) {
            throw new IllegalArgumentException(NO_SUCH_TRADING_ACCOUNT);
        }
        BuyTransactionResource resource = new BuyTransactionResource(transactionDto.getBoughtTypeCurrency());
        switch (transactionDto.getBoughtTypeCurrency()) {
            case EUR:
                resource.setBoughtTypeInitialAmount(account.getEurAmount());
                account.setEurAmount(account.getEurAmount() + transactionDto.getAmount());
                break;
            case TRY:
                resource.setBoughtTypeInitialAmount(account.getTryAmount());
                account.setTryAmount(account.getTryAmount() + transactionDto.getAmount());
                break;
            case USD:
                resource.setBoughtTypeInitialAmount(account.getUsdAmount());
                account.setUsdAmount(account.getUsdAmount() + transactionDto.getAmount());
                break;
            case CNY:
                resource.setBoughtTypeInitialAmount(account.getCnyAmount());
                account.setCnyAmount(account.getCnyAmount() + transactionDto.getAmount());
                break;
            case GBP:
                resource.setBoughtTypeInitialAmount(account.getGbpAmount());
                account.setGbpAmount(account.getGbpAmount() + transactionDto.getAmount());
                break;
            case JPY:
                resource.setBoughtTypeInitialAmount(account.getJpyAmount());
                account.setJpyAmount(account.getJpyAmount() + transactionDto.getAmount());
                break;
        }
        resource.setBoughtTypeLastAmount(resource.getBoughtTypeInitialAmount() + transactionDto.getAmount());
        repository.saveAndFlush(account);
        return resource;
    }

    @Transactional
    public ExchangeTransactionResource exchangeFunds(ExchangeTransactionDto transactionDto, UUID userId) {

        ExchangeTransactionResource resource = new ExchangeTransactionResource();
        UserTradingAccount account = repository.findUserTradingAccountByUser(userRepository.findById(userId));
        //Convert Currency
        CurrencyConverterDto converterDto = new CurrencyConverterDto();
        converterDto.setInputCurrencyType(transactionDto.getSoldTypeCurrency());
        converterDto.setOutputCurrencyType(transactionDto.getBoughtTypeCurrency());
        converterDto.setAmount(transactionDto.getAmountOfSold());
        CurrencyConverterResource converterResource = currencyRateService.convertCurrency(converterDto);

        resource.setRate(converterResource.getRate());

        //Decrease fund which is sold
        BuyTransactionResource buyTransactionResource = buyFund(new BuyTransactionDto(converterDto.getInputCurrencyType(), -transactionDto.getAmountOfSold()), userId);
        resource.setSoldTypeInitialAmount(buyTransactionResource.getBoughtTypeInitialAmount());
        resource.setSoldTypeLastAmount(buyTransactionResource.getBoughtTypeLastAmount());
        resource.setSoldTypeCurrency(buyTransactionResource.getBoughtTypeCurrency());

        //Increase Fund which is bought
        buyTransactionResource = buyFund(new BuyTransactionDto(converterDto.getOutputCurrencyType(), converterResource.getAmount()), userId);
        resource.setBoughtTypeInitialAmount(buyTransactionResource.getBoughtTypeInitialAmount());
        resource.setBoughtTypeLastAmount(buyTransactionResource.getBoughtTypeLastAmount());
        resource.setBoughtTypeCurrency(buyTransactionResource.getBoughtTypeCurrency());
        return resource;
    }
}
