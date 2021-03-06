package com.eyetrade.backend.repository;

import com.eyetrade.backend.constants.CurrencyType;
import com.eyetrade.backend.model.entity.PortfolioFollowsCurrency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PortfolioFollowsCurrencyRepository extends JpaRepository<PortfolioFollowsCurrency, UUID> {

    Long deleteByFollowerPortfolioId(UUID portfolioId);

    Long deleteByFirstCurrencyTypeAndSecondCurrencyTypeAndFollowerPortfolioId(CurrencyType currency1, CurrencyType currency2, UUID portfolioId);

    List<PortfolioFollowsCurrency> findByFollowerPortfolioId(UUID portfolioId);

    boolean existsByFirstCurrencyTypeAndSecondCurrencyTypeAndFollowerPortfolioId(CurrencyType currency1, CurrencyType currency2, UUID portfolioId);

}
