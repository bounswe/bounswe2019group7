package com.eyetrade.backend.repository;

import com.eyetrade.backend.model.entity.EventRssFeedMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRssFeedMessageRepository extends JpaRepository<EventRssFeedMessage,String> {
}
