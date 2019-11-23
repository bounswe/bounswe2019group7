package com.eyetrade.backend.service;

import com.eyetrade.backend.constants.CurrencyType;
import com.eyetrade.backend.model.entity.CurrencyFollowing;
import com.eyetrade.backend.model.entity.Portfolio;
import com.eyetrade.backend.model.entity.User;
import com.eyetrade.backend.model.resource.currency.CurrencyFollowingResource;
import com.eyetrade.backend.model.resource.portfolio.PortfolioResource;
import com.eyetrade.backend.model.resource.portfolio.PortfoliosResource;
import com.eyetrade.backend.repository.CurrencyFollowingRepository;
import com.eyetrade.backend.repository.PortfolioRepository;
import com.eyetrade.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.eyetrade.backend.constants.ErrorConstants.FOLLOWING_RELATION_ALREADY_EXISTS;
import static com.eyetrade.backend.constants.GeneralConstants.DB_DATE_TIME_FORMAT;
import static com.eyetrade.backend.utils.DateUtils.dateTimeFormatter;

@Service
public class PortfolioService {

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private CurrencyFollowingRepository followingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CurrencyFollowingService currencyFollowingService;

    public PortfolioResource createPortfolio(String portfolioName, UUID ownerID){
        Portfolio portfolio = new Portfolio();
        portfolio.setName(portfolioName);
        portfolio.setOwnerID(ownerID.toString());
        portfolioRepository.save(portfolio);
        return new PortfolioResource(portfolio.getId(),portfolioName,ownerID.toString());
    }

    public PortfolioResource addCurrency(UUID ownerID, CurrencyType currencyType, UUID portfolioID){
        User user = userRepository.findById(ownerID);
        if (followingRepository.existsByBaseCurrencyTypeAndFollower(currencyType,user)){
            throw new IllegalArgumentException(FOLLOWING_RELATION_ALREADY_EXISTS);
        }
        CurrencyFollowing relation = new CurrencyFollowing();
        relation.setFollower(user);
        relation.setBaseCurrencyType(currencyType);
        relation.setPortfolioID(portfolioID.toString());
        relation.setFollowingDate(dateTimeFormatter(new Date(),DB_DATE_TIME_FORMAT));
        followingRepository.saveAndFlush(relation);
        Portfolio portfolio = portfolioRepository.findPortfolioById(portfolioID);
        return new PortfolioResource(portfolio.getId(),portfolio.getName(),ownerID.toString());

    }

    public PortfoliosResource getPortfolios(UUID ownerID){
        List<PortfolioResource> resources = new ArrayList<PortfolioResource>();
        List<Portfolio> portfolios = portfolioRepository.findAllByOwnerID(ownerID.toString());

        for(Portfolio portfolio: portfolios){
            resources.add(new PortfolioResource(portfolio.getId(),portfolio.getName(),ownerID.toString()));
        }

        return new PortfoliosResource(resources);
    }

    public CurrencyFollowingResource getCurrencies(String portfolioID){
        return currencyFollowingService.getFollowings(portfolioID);
    }
}

