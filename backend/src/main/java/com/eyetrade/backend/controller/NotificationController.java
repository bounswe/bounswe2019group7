package com.eyetrade.backend.controller;

import com.eyetrade.backend.model.resource.notification.NotificationResource;
import com.eyetrade.backend.security.JwtUserChecker;
import com.eyetrade.backend.service.NotificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Api(value = "Notifications", tags = {"Operations Related With Notifications"})
@RestController
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private JwtUserChecker jwtUserChecker;

    @ApiOperation(value = "Get current user's notifications", response = NotificationResource.class, responseContainer = "List")
    @GetMapping("/self_notifications")
    public ResponseEntity<List<NotificationResource>> getAllSelfNotifications(@RequestHeader("Authorization") String token)
            throws IllegalAccessException {
        UUID userId = jwtUserChecker.resolveBasicToken(token);
        List<NotificationResource> notifications = notificationService.getAllSelfNotifications(userId);
        return ResponseEntity.ok(notifications);
    }

    @ApiOperation(value = "Delete a notification by giving it's id. However, the notification must belong to the user",
            response = String.class)
    @DeleteMapping("/delete_notification")
    public ResponseEntity<String> deleteNotification
            (@RequestHeader("Authorization") String token,
             @RequestHeader("notification_id") UUID notificationId)
            throws IllegalAccessException {
        UUID userId = jwtUserChecker.resolveBasicToken(token);
        notificationService.deleteNotification(userId, notificationId);
        return ResponseEntity.ok("Success");
    }

    @ApiOperation(value = "Delete all the notifications belong to the user",
            response = String.class)
    @DeleteMapping("/delete_self_notifications")
    public ResponseEntity<String> deleteAllSelfNotifications
            (@RequestHeader("Authorization") String token)
            throws IllegalAccessException {
        UUID userId = jwtUserChecker.resolveBasicToken(token);
        notificationService.getAllSelfNotifications(userId);
        return ResponseEntity.ok("Success");
    }

    @ApiOperation(value = "Mark a notification as seen by giving it's id. However, the notification must belong to the user",
            response = String.class)
    @PutMapping("/see_notification")
    public ResponseEntity<String> setNotificationAsSeen
            (@RequestHeader("Authorization") String token,
             @RequestHeader("notification_id") UUID notificationId)
            throws IllegalAccessException {
        UUID userId = jwtUserChecker.resolveBasicToken(token);
        notificationService.setNotificationAsSeen(userId, notificationId);
        return ResponseEntity.ok("Success");
    }

    @ApiOperation(value = "Mark all the notifications as seen which belong to the user",
            response = String.class)
    @PutMapping("/see_self_notifications")
    public ResponseEntity<String> setAllSelfNotificationAsSeen
            (@RequestHeader("Authorization") String token)
            throws IllegalAccessException {
        UUID userId = jwtUserChecker.resolveBasicToken(token);
        notificationService.setAllSelfNotificationAsSeen(userId);
        return ResponseEntity.ok("Success");
    }

}
