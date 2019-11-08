package com.eyetrade.backend.repository;

import com.eyetrade.backend.model.entity.EventRssFeed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRssFeedRepository extends JpaRepository<EventRssFeed,String> {
}
