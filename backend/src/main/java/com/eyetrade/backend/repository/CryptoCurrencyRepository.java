package com.eyetrade.backend.repository;

import com.eyetrade.backend.model.entity.CryptoCurrencyRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface CryptoCurrencyRepository extends JpaRepository<CryptoCurrencyRecord, UUID> {

    @Query(
            value = "SELECT * FROM crypto_currency_records order by date desc LIMIT 1",
            nativeQuery = true)
    CryptoCurrencyRecord findLastRecord();

    List<CryptoCurrencyRecord> findCryptoCurrencyRecordsByDateBetweenOrderByDate(String startDate, String endDate);

    List<CryptoCurrencyRecord> findCryptoCurrencyRecordsByDateAfterOrderByDate(String startDate);

}
