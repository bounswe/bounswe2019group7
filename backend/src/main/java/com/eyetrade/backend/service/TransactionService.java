package com.eyetrade.backend.service;

import com.eyetrade.backend.mapper.BuySellOrderMapper;
import com.eyetrade.backend.mapper.UserAccountMapper;
import com.eyetrade.backend.model.dto.currency.CurrencyConverterDto;
import com.eyetrade.backend.model.dto.transaction.BuyTransactionDto;
import com.eyetrade.backend.model.dto.transaction.ExchangeTransactionDto;
import com.eyetrade.backend.model.entity.BuySellOrder;
import com.eyetrade.backend.model.entity.CurrencyRecord;
import com.eyetrade.backend.model.dto.transaction.SellTransactionDto;
import com.eyetrade.backend.model.entity.User;
import com.eyetrade.backend.model.entity.UserTradingAccount;
import com.eyetrade.backend.model.resource.currency.CurrencyConverterResource;
import com.eyetrade.backend.model.resource.transaction.BuySellOrderResource;
import com.eyetrade.backend.model.resource.transaction.BuyTransactionResource;
import com.eyetrade.backend.model.resource.transaction.ExchangeTransactionResource;
import com.eyetrade.backend.model.resource.transaction.SellTransactionResource;
import com.eyetrade.backend.model.resource.transaction.UserAccountResource;
import com.eyetrade.backend.repository.BuySellOrderRepository;
import com.eyetrade.backend.repository.UserRepository;
import com.eyetrade.backend.repository.UserTradingAccountRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
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

    @Autowired
    private BuySellOrderService buySellOrderService;

    @Autowired
    private BuySellOrderRepository buySellOrderRepository;

    @Autowired
    private BuySellOrderMapper buySellOrderMapper;

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
        Double lastAmount=0.0;
        if (account == null) {
            throw new IllegalArgumentException(NO_SUCH_TRADING_ACCOUNT);
        }
        BuyTransactionResource resource = new BuyTransactionResource(transactionDto.getBoughtTypeCurrency());
        switch (transactionDto.getBoughtTypeCurrency()) {
            case EUR:
                resource.setBoughtTypeInitialAmount(account.getEurAmount());
                lastAmount=resource.getBoughtTypeInitialAmount() + transactionDto.getAmount();
                if(lastAmount>0){
                    account.setEurAmount(lastAmount);
                    resource.setIsSuccessful(true);
                }
                break;
            case TRY:
                resource.setBoughtTypeInitialAmount(account.getTryAmount());
                lastAmount=resource.getBoughtTypeInitialAmount() + transactionDto.getAmount();
                if(lastAmount>0){
                    account.setTryAmount(lastAmount);
                    resource.setIsSuccessful(true);
                }break;
            case USD:
                resource.setBoughtTypeInitialAmount(account.getUsdAmount());
                lastAmount=resource.getBoughtTypeInitialAmount() + transactionDto.getAmount();
                if(lastAmount>0){
                    account.setUsdAmount(lastAmount);
                    resource.setIsSuccessful(true);
                }break;
            case CNY:
                resource.setBoughtTypeInitialAmount(account.getCnyAmount());
                lastAmount=resource.getBoughtTypeInitialAmount() + transactionDto.getAmount();
                if(lastAmount>0){
                    account.setCnyAmount(lastAmount);
                    resource.setIsSuccessful(true);
                } break;
            case GBP:
                resource.setBoughtTypeInitialAmount(account.getGbpAmount());
                lastAmount=resource.getBoughtTypeInitialAmount() + transactionDto.getAmount();
                if(lastAmount>0){
                    account.setGbpAmount(lastAmount);
                    resource.setIsSuccessful(true);
                }break;
            case JPY:
                resource.setBoughtTypeInitialAmount(account.getJpyAmount());
                lastAmount=resource.getBoughtTypeInitialAmount() + transactionDto.getAmount();
                if(lastAmount>0){
                    account.setJpyAmount(lastAmount);
                    resource.setIsSuccessful(true);
                }break;
        }
        repository.saveAndFlush(account);
        return resource;
    }

    @Transactional
    public SellTransactionResource sellfund(SellTransactionDto dto, UUID userId){
        SellTransactionResource resource= new SellTransactionResource();
        BuyTransactionResource buyTransactionResource=buyFund(new BuyTransactionDto(dto.getSoldTypeCurrency(),-dto.getAmount()),userId);
        resource.setSoldTypeInitialAmount(buyTransactionResource.getBoughtTypeInitialAmount());
        resource.setSoldTypeLastAmount(buyTransactionResource.getBoughtTypeLastAmount());
        resource.setSoldTypeCurrency(buyTransactionResource.getBoughtTypeCurrency());
        return resource;
    }

    @Transactional
    public ExchangeTransactionResource exchangeFunds(ExchangeTransactionDto transactionDto, UUID userId) {

        ExchangeTransactionResource resource = new ExchangeTransactionResource();
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

        //Increase Fund which is bought if sold is completed
        if(!buyTransactionResource.getIsSuccessful()){

            resource.setBoughtTypeInitialAmount(converterResource.getAmount());
            resource.setBoughtTypeLastAmount(converterResource.getAmount());
            resource.setBoughtTypeCurrency(converterDto.getOutputCurrencyType());
            resource.setIsSuccessful(false);
            return resource;
        }
        buyTransactionResource = buyFund(new BuyTransactionDto(converterDto.getOutputCurrencyType(), converterResource.getAmount()), userId);
        resource.setBoughtTypeInitialAmount(buyTransactionResource.getBoughtTypeInitialAmount());
        resource.setBoughtTypeLastAmount(buyTransactionResource.getBoughtTypeLastAmount());
        resource.setBoughtTypeCurrency(buyTransactionResource.getBoughtTypeCurrency());

        resource.setIsSuccessful(true);
        return resource;
    }

    @Transactional
    @Modifying
    public void checkBuySellOrder() {
        List<BuySellOrderResource> buySellOrderResources = buySellOrderService.getAllUncompletedOrders();
        CurrencyRecord record=currencyRateService.findLastRecord();
        for (BuySellOrderResource resource : CollectionUtils.emptyIfNull(buySellOrderResources)) {

            if (resource.getSoldType() == null && resource.getBoughtType() != null) {
                Double rateOfBoughtType = currencyRateService.findRate(resource.getBoughtType(), record);
                if (rateOfBoughtType < resource.getMaxRate() && rateOfBoughtType > resource.getMinRate()) {
                    buyFund(new BuyTransactionDto(resource.getBoughtType(), resource.getBoughtAmount()), resource.getUser().getId());
                    resource.setIsCompleted(true);
                    buySellOrderRepository.save(buySellOrderMapper.resourceToEntity(resource));
                }
            } else if (resource.getSoldType() != null && resource.getBoughtType() == null) {
                Double rateOfSoldType = currencyRateService.findRate(resource.getSoldType(), record);
                if (rateOfSoldType < resource.getMaxRate() && rateOfSoldType > resource.getMinRate()) {
                    buyFund(new BuyTransactionDto(resource.getSoldType(), -resource.getSoldAmount()), resource.getUser().getId());
                    resource.setIsCompleted(true);
                    buySellOrderRepository.save(buySellOrderMapper.resourceToEntity(resource));
                }
            } else if (resource.getSoldType() != null && resource.getBoughtType() != null) {
                Double rateOfBoughtType = currencyRateService.findRate(resource.getBoughtType(), record);
                Double rateOfSoldType = currencyRateService.findRate(resource.getSoldType(), record);
                Double exchangeRate = rateOfBoughtType / rateOfSoldType;
                if (exchangeRate < resource.getMaxRate() && exchangeRate > resource.getMinRate()) {
                    BuyTransactionResource buyTransactionResource=buyFund(new BuyTransactionDto(resource.getSoldType(), -resource.getSoldAmount()), resource.getUser().getId());
                    if(buyTransactionResource.getIsSuccessful()){
                        buyFund(new BuyTransactionDto(resource.getBoughtType(), resource.getSoldAmount() * exchangeRate), resource.getUser().getId());
                        resource.setIsCompleted(true);
                        buySellOrderRepository.save(buySellOrderMapper.resourceToEntity(resource));
                    }
                }   
            }
        }
    }
}
