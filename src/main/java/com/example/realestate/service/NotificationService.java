package com.example.realestate.service;

import com.example.realestate.models.Notification;
import com.example.realestate.models.NotificationSetting;
import com.example.realestate.models.User; // 👈 User class එක import කළා
import com.example.realestate.repositories.NotificationRepository;
import com.example.realestate.repositories.NotificationSettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationSettingRepository settingRepository;

    public List<Notification> getNotificationsForUser(Long userId) {

        return notificationRepository.findByRecipientIdOrderByCreatedAtDesc(userId);
    }

    public Notification createNotification(Long recipientId, Notification.NotificationType type, String title, String message, Long relatedId) {

        NotificationSetting settings = settingRepository.findById(recipientId)
                .orElseGet(() -> {
                    NotificationSetting defaultSetting = new NotificationSetting();
                    defaultSetting.setUserId(recipientId);
                    defaultSetting.setMeetingReminders(true);
                    defaultSetting.setInquiryReminders(true);
                    defaultSetting.setEmailNotifications(false);
                    return defaultSetting;
                });


        if (type == Notification.NotificationType.MEETING_REMINDER && !settings.isMeetingReminders()) return null;
        if (type == Notification.NotificationType.INQUIRY_REMINDER && !settings.isInquiryReminders()) return null;

        Notification n = new Notification();


        User recipient = new User();
        recipient.setId(recipientId);
        n.setRecipient(recipient);

        n.setType(type);
        n.setTitle(title);
        n.setMessage(message);
        n.setRelatedEntityId(relatedId);

        if (settings.isEmailNotifications()) {
            System.out.println("Email sent to user " + recipientId + ": " + title);
        }

        return notificationRepository.save(n);
    }

    public Notification markAsRead(Long id) {
        Notification n = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        n.setRead(true);
        return notificationRepository.save(n);
    }

    public void markAllAsRead(Long userId) {
        List<Notification> list = notificationRepository.findByRecipientIdOrderByCreatedAtDesc(userId);
        List<Notification> toUpdate = new ArrayList<>();

        for (Notification n : list) {
            if (!n.isRead()) {
                n.setRead(true);
                toUpdate.add(n);
            }
        }

        if (!toUpdate.isEmpty()) {
            notificationRepository.saveAll(toUpdate);
        }
    }

    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
    }

    public NotificationSetting getSettings(Long userId) {
        return settingRepository.findById(userId)
                .orElseGet(() -> {
                    NotificationSetting defaultSetting = new NotificationSetting();
                    defaultSetting.setUserId(userId);
                    defaultSetting.setMeetingReminders(true);
                    defaultSetting.setInquiryReminders(true);
                    defaultSetting.setEmailNotifications(false);
                    return defaultSetting;
                });
    }

    public NotificationSetting updateSettings(Long userId, NotificationSetting newSettings) {
        NotificationSetting existing = settingRepository.findById(userId)
                .orElse(new NotificationSetting());

        existing.setUserId(userId);
        existing.setMeetingReminders(newSettings.isMeetingReminders());
        existing.setInquiryReminders(newSettings.isInquiryReminders());
        existing.setEmailNotifications(newSettings.isEmailNotifications());

        return settingRepository.save(existing);
    }
}