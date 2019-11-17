package com.eyetrade.backend.mapper;

import com.eyetrade.backend.model.entity.Notification;
import com.eyetrade.backend.model.resource.notification.NotificationResource;

import java.util.Date;

public class NotificationMapper {

    public static NotificationResource entityToResource(Notification notification){
        NotificationResource resource = new NotificationResource();
        resource.setId(notification.getId());
        resource.setFollowerEmail(notification.getFollower().getEmail());
        resource.setFollowerName(notification.getFollower().getName());
        resource.setFollowerSurname(notification.getFollower().getSurname());
        resource.setNotificationDate(new Date(notification.getNotificationDate()));
        resource.setSeen(notification.isSeen());
        return resource;
    }

}
