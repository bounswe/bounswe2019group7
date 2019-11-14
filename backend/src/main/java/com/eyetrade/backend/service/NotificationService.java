package com.eyetrade.backend.service;

import com.eyetrade.backend.model.entity.Notification;
import com.eyetrade.backend.model.entity.User;
import com.eyetrade.backend.repository.NotificationRepository;
import com.eyetrade.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void createNotification(User follower, User following){
        Notification notification = new Notification();
        notification.setNotificationOwner(following);
        notification.setFollower(follower);
        notification.setNotificationDate(new Date().getTime());
        notification.setSeen(false); // when notification is created it is not seen yet
        notificationRepository.save(notification);
    }

    @Transactional
    public void deleteNotification(UUID userId, UUID notificationId) throws IllegalAccessException {
        Notification notification = notificationRepository.findNotificationById(notificationId);
        if(!notification.getNotificationOwner().getId().equals(notification.getNotificationOwner().getId())){
            throw new IllegalAccessException();
        }
        notificationRepository.deleteById(notificationId);
    }

    @Transactional
    public void deleteAllSelfNotifications(UUID userId){
        User user = userRepository.findById(userId);
        notificationRepository.deleteByNotificationOwner(user);
    }

    @Transactional
    public void setNotificationAsSeen(UUID userId, UUID notificationId) throws IllegalAccessException {
        Notification notification = notificationRepository.findNotificationById(notificationId);
        if(!notification.getNotificationOwner().getId().equals(notification.getNotificationOwner().getId())){
            throw new IllegalAccessException();
        }
        notification.setSeen(true);
        notificationRepository.save(notification);
    }

    @Transactional
    public void setAllSelfNotificationAsSeen(UUID userId) throws IllegalAccessException {
        User user = userRepository.findById(userId);
        List<Notification> notifications = notificationRepository.findNotificationByNotificationOwner(user);
        for(Notification notification : notifications) {
            notification.setSeen(true);
        }
        notificationRepository.saveAll(notifications);
    }




}
