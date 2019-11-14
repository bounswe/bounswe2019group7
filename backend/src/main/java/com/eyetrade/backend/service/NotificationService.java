package com.eyetrade.backend.service;

import com.eyetrade.backend.model.entity.Notification;
import com.eyetrade.backend.model.entity.User;
import com.eyetrade.backend.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Transactional
    public void createNotification(User follower, User following){
        Notification notification = new Notification();
        notification.setNotificationOwner(following);
        notification.setFollower(follower);
        notification.setNotificationDate(new Date().getTime());
        notification.setSeen(false); // when notification is created it is not seen yet
        notificationRepository.saveAndFlush(notification);
    }

}
