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

@Api(value = "Notifications", tags = {"Notifications"})
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
             @RequestHeader("notification_id") UUID notificationId) {
        try {
            UUID userId = jwtUserChecker.resolveBasicToken(token);
            notificationService.deleteNotification(userId, notificationId);
        } catch (IllegalAccessException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Success");
    }

    @ApiOperation(value = "Delete all the notifications belong to the user",
            response = String.class)
    @DeleteMapping("/delete_self_notifications")
    public ResponseEntity<String> deleteAllSelfNotifications
            (@RequestHeader("Authorization") String token) {
        UUID userId = null;

        notificationService.deleteAllSelfNotifications(userId);
        return ResponseEntity.ok("Success");
    }

    @ApiOperation(value = "Mark a notification as seen by giving it's id. However, the notification must belong to the user",
            response = String.class)
    @PutMapping("/mark_notification")
    public ResponseEntity<String> setNotificationAsSeen
            (@RequestHeader("Authorization") String token,
             @RequestHeader("notification_id") UUID notificationId) {
        try {
            UUID userId = jwtUserChecker.resolveBasicToken(token);
            notificationService.setNotificationAsSeen(userId, notificationId);
        } catch (IllegalAccessException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Success");
    }

    @ApiOperation(value = "Mark all the notifications as seen which belong to the user",
            response = String.class)
    @PutMapping("/mark_self_notifications")
    public ResponseEntity<String> setAllSelfNotificationAsSeen
            (@RequestHeader("Authorization") String token) {
        UUID userId;
        try {
            userId = jwtUserChecker.resolveBasicToken(token);
            notificationService.setAllSelfNotificationAsSeen(userId);
        } catch (IllegalAccessException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Success");
    }

}
