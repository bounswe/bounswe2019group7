package com.eyetrade.backend.model.resource.notification;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

@Resource
@Data
@NoArgsConstructor
public class NotificationResource {

    private UUID id;

    private String followerEmail;

    private String followerName;

    private String followerSurname;

    private Date notificationDate;

    private boolean isSeen;

}
