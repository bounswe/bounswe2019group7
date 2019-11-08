package com.eyetrade.backend.repository;

import com.eyetrade.backend.model.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;


public interface EventRepository extends JpaRepository<Event,String> {
    Event findEventRssFeedMessageByGuid(UUID guid);
    boolean existsByGuid(UUID guid);
    List<Event> findAllByOrderByAdditionDateDesc();
}
