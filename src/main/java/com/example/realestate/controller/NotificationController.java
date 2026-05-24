package com.example.realestate.controller;

import com.example.realestate.models.Notification;
import com.example.realestate.models.NotificationSetting;
import com.example.realestate.service.NotificationService; // 🚀 Fixed package path (services -> service)
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "*")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;


    @GetMapping("/{userId}")
    public ResponseEntity<List<Notification>> getNotifications(@PathVariable Long userId) {
        return ResponseEntity.ok(notificationService.getNotificationsForUser(userId));
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<Notification> markRead(@PathVariable Long id) {
        return ResponseEntity.ok(notificationService.markAsRead(id));
    }

    @PutMapping("/{userId}/read-all")
    public ResponseEntity<String> markAllRead(@PathVariable Long userId) {
        notificationService.markAllAsRead(userId);
        return ResponseEntity.ok("All notifications marked as read");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.ok("Deleted notification");
    }


    @GetMapping("/{userId}/settings")
    public ResponseEntity<NotificationSetting> getSettings(@PathVariable Long userId) {
        return ResponseEntity.ok(notificationService.getSettings(userId));
    }


    @PutMapping("/{userId}/settings")
    public ResponseEntity<NotificationSetting> updateSettings(@PathVariable Long userId, @RequestBody NotificationSetting settings) {
        return ResponseEntity.ok(notificationService.updateSettings(userId, settings));
    }
}