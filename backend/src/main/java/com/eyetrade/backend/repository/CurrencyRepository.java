package com.eyetrade.backend.repository;

import com.eyetrade.backend.model.entity.CurrencyRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository<CurrencyRecord,String> {
    CurrencyRecord findTopByOrderByTimestamp();
}
