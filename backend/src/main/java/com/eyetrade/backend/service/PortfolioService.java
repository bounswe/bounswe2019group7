package com.eyetrade.backend.service;

import com.eyetrade.backend.constants.CurrencyType;
import com.eyetrade.backend.constants.ErrorConstants;
import com.eyetrade.backend.model.entity.CurrencyRecord;
import com.eyetrade.backend.model.entity.PortfolioFollowsCurrency;
import com.eyetrade.backend.model.entity.Portfolio;
import com.eyetrade.backend.model.resource.portfolio.PortfolioCurrencyPair;
import com.eyetrade.backend.model.resource.portfolio.PortfolioResource;
import com.eyetrade.backend.model.resource.portfolio.PortfoliosResource;
import com.eyetrade.backend.repository.PortfolioFollowsCurrencyRepository;
import com.eyetrade.backend.repository.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

import static com.eyetrade.backend.constants.ErrorConstants.FOLLOWING_RELATION_ALREADY_EXISTS;
import static com.eyetrade.backend.constants.GeneralConstants.DB_DATE_TIME_FORMAT;
import static com.eyetrade.backend.utils.DateUtils.dateTimeFormatter;

@Service
public class PortfolioService {

    @Autowired
    private CurrencyRateService currencyRateService;

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private PortfolioFollowsCurrencyRepository portfolioFollowsCurrencyRepository;

    @Transactional
    public PortfolioResource createPortfolio(String portfolioName, UUID ownerId){
        Portfolio portfolio = new Portfolio();
        portfolio.setName(portfolioName);
        portfolio.setOwnerId(ownerId);
        portfolioRepository.save(portfolio);
        return new PortfolioResource(portfolio.getId(), portfolioName, ownerId, new ArrayList<>(), new ArrayList<>());
    }

    @Transactional
    public PortfolioResource deletePortfolio(UUID portfolioId, UUID ownerId) throws IllegalAccessException {
        Portfolio portfolio = portfolioRepository.findPortfolioById(portfolioId);
        // check whether the portfolio belongs the user
        if(ownerId != portfolio.getOwnerId()){
            throw new IllegalAccessException(ErrorConstants.PORTFOLIO_DOES_NOT_BELONG_TO_USER);
        }
        PortfolioResource resource = createPortfolioResource(portfolio);
        // delete the portfolio
        portfolioRepository.deleteById(portfolioId);
        // delete the portfolio-currency relations
        portfolioFollowsCurrencyRepository.deleteByFollowerPortfolioId(portfolioId);
        return resource;
    }

    @Transactional
    public PortfolioResource addCurrency(CurrencyType currencyType, UUID portfolioId){
        if(portfolioFollowsCurrencyRepository.existsByBaseCurrencyTypeAndFollowerPortfolioId(currencyType, portfolioId)){
            throw new IllegalArgumentException(FOLLOWING_RELATION_ALREADY_EXISTS);
        }
        PortfolioFollowsCurrency relation = new PortfolioFollowsCurrency();
        relation.setBaseCurrencyType(currencyType);
        relation.setFollowerPortfolioId(portfolioId);
        relation.setFollowingDate(dateTimeFormatter(new Date(), DB_DATE_TIME_FORMAT));
        portfolioFollowsCurrencyRepository.save(relation);
        Portfolio portfolio = portfolioRepository.findPortfolioById(portfolioId);
        return createPortfolioResource(portfolio);
    }

    public PortfoliosResource getPortfolios(UUID ownerId){
        List<PortfolioResource> resources = new ArrayList<>();
        List<Portfolio> portfolios = portfolioRepository.findByOwnerId(ownerId);
        for(Portfolio portfolio: portfolios){
            PortfolioResource resource = createPortfolioResource(portfolio);
            resources.add(resource);
        }
        return new PortfoliosResource(resources);
    }

    private PortfolioResource createPortfolioResource(Portfolio portfolio){
        PortfolioResource portfolioResource = new PortfolioResource();
        List<CurrencyType> currencies = getAllCurrenciesOfPortfolio(portfolio);
        List<PortfolioCurrencyPair> currencyPairs = getAllPairsRelatedWithPortfolio(currencies);
        portfolioResource.setId(portfolio.getId());
        portfolioResource.setName(portfolio.getName());
        portfolioResource.setOwnerId(portfolio.getOwnerId());
        portfolioResource.setCurrencyTypes(currencies);
        portfolioResource.setCurrencyPairs(currencyPairs);
        return portfolioResource;
    }

    private List<CurrencyType> getAllCurrenciesOfPortfolio(Portfolio portfolio){
        List<CurrencyType> currencies = new ArrayList<>();
        List<PortfolioFollowsCurrency> relations = portfolioFollowsCurrencyRepository.findByFollowerPortfolioId(portfolio.getId());
        for(PortfolioFollowsCurrency relation : relations){
            currencies.add(relation.getBaseCurrencyType());
        }
        return currencies;
    }

    private List<PortfolioCurrencyPair> getAllPairsRelatedWithPortfolio(List<CurrencyType> portfolioCurrencies){
        List<PortfolioCurrencyPair> currencyPairs = new ArrayList<>();
        CurrencyRecord lastRecord = currencyRateService.findLastRecord();
        int n = portfolioCurrencies.size();
        for(int i = 0; i < n; i++){
            for(int j = i+1; j < n; j++){
                CurrencyType firstType = portfolioCurrencies.get(i);
                CurrencyType secondType = portfolioCurrencies.get(j);
                double firstRate = currencyRateService.findRate(firstType, lastRecord);
                double secondRate = currencyRateService.findRate(secondType, lastRecord);
                PortfolioCurrencyPair pair = new PortfolioCurrencyPair(firstType, secondType, firstRate / secondRate);
                currencyPairs.add(pair);
            }
        }
        return currencyPairs;
    }



}

