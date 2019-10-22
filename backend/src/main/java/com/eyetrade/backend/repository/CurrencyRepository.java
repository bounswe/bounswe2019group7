package com.eyetrade.backend.repository;

import com.eyetrade.backend.model.entity.CurrencyRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository<CurrencyRecord,String> {
    @Query(
            value = "SELECT * FROM currency_records order by timestamp desc LIMIT 1",
            nativeQuery = true)
    CurrencyRecord findLastRecord();
}
