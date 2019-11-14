package com.eyetrade.backend.repository;

import com.eyetrade.backend.model.entity.Notification;
import com.eyetrade.backend.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {

    Notification findNotificationById(UUID id);

    List<Notification> findNotificationByNotificationOwner(User user);

    @Modifying
    void deleteByNotificationOwner(User user);

    @Modifying
    void deleteById(UUID id);

}
