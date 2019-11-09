package com.eyetrade.backend.repository;

import com.eyetrade.backend.model.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event,UUID> {

    Event findEventByGuid(UUID guid);

    boolean existsByGuid(UUID guid);

    List<Event> findAllByOrderByAdditionDateDesc();

    @Modifying
    void deleteByAdditionDateBefore(Date expiryDate);
}
