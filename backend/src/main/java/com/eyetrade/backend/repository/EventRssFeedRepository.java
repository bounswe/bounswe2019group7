package com.eyetrade.backend.repository;

import com.eyetrade.backend.model.entity.EventRssFeed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.UUID;

@Repository
public interface EventRssFeedRepository extends JpaRepository<EventRssFeed, UUID> {

    @Modifying
    void deleteByAdditionDateBefore(Date expiryDate);
}
