package com.eyetrade.backend.service;

import com.eyetrade.backend.constants.CurrencyType;
import com.eyetrade.backend.model.entity.CurrencyFollowing;
import com.eyetrade.backend.model.entity.CurrencyRecord;
import com.eyetrade.backend.model.entity.User;
import com.eyetrade.backend.model.resource.currency.CurrencyFollowingResource;
import com.eyetrade.backend.repository.CurrencyFollowingRepository;
import com.eyetrade.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.UUID;

import static com.eyetrade.backend.constants.ErrorConstants.FOLLOWING_RELATION_ALREADY_EXISTS;
import static com.eyetrade.backend.constants.ErrorConstants.NO_SUCH_FOLLOWING;
import static com.eyetrade.backend.constants.GeneralConstants.DB_DATE_TIME_FORMAT;
import static com.eyetrade.backend.utils.DateUtils.dateTimeFormatter;

/**
 * Created by Emir Gökdemir
 * on 2 Kas 2019
 */
@Service
public class CurrencyFollowingService {

    @Autowired
    private CurrencyFollowingRepository followingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CurrencyConverterService converterService;

    @Autowired
    private CurrencyRecordService currencyRecordService;

    @Transactional
    public CurrencyFollowingResource followCurrency(UUID followerId, CurrencyType baseCurrencyType){
        // find both of the users
        User follower = userRepository.findById(followerId);
        if (followingRepository.existsByBaseCurrencyTypeAndFollower(baseCurrencyType,follower)){
            throw new IllegalArgumentException(FOLLOWING_RELATION_ALREADY_EXISTS);
        }
        CurrencyFollowing relation = new CurrencyFollowing();
        relation.setFollower(follower);
        relation.setBaseCurrencyType(baseCurrencyType);
        relation.setFollowingDate(dateTimeFormatter(new Date(),DB_DATE_TIME_FORMAT));
        followingRepository.saveAndFlush(relation);
        return getFollowings(followerId);
    }

    @Transactional
    public CurrencyFollowingResource getFollowings(UUID followerId){
        User follower = userRepository.findById(followerId);
        CurrencyFollowing relation=followingRepository.findByFollower(follower);
        if (relation==null){
            throw new NoSuchElementException(NO_SUCH_FOLLOWING);
        }
        CurrencyRecord record=currencyRecordService.updateIfCurrenciesExpiredAndGetLastRecord();
        Double baseRate=converterService.findRate(relation.getBaseCurrencyType(),record);
        return new CurrencyFollowingResource(
                baseRate/record.getTurkishLiraRate(),
                baseRate/record.getDollarRate(),
                baseRate/record.getEuroRate(),
                baseRate/record.getSterlingRate());
    }

    @Transactional
    public CurrencyFollowingResource getFollowings(String portfolioID){
        CurrencyFollowing relation=followingRepository.findByPortfolioID(portfolioID);
        if (relation==null){
            throw new NoSuchElementException(NO_SUCH_FOLLOWING);
        }
        CurrencyRecord record=currencyRecordService.updateIfCurrenciesExpiredAndGetLastRecord();
        Double baseRate=converterService.findRate(relation.getBaseCurrencyType(),record);
        return new CurrencyFollowingResource(
                baseRate/record.getTurkishLiraRate(),
                baseRate/record.getDollarRate(),
                baseRate/record.getEuroRate(),
                baseRate/record.getSterlingRate());
    }
}
