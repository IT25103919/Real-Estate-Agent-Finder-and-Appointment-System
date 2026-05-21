package com.example.realestate.models;

import jakarta.persistence.*;

@Entity
@Table(name = "notification_settings")
public class NotificationSetting {

    @Id
    private Long userId;

    // වෙනස් කළ කොටස: User සමඟ One-to-One සම්බන්ධ කර Primary Key එක Share කරගැනීම
    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "meeting_reminders")
    private boolean meetingReminders = true;

    @Column(name = "inquiry_reminders")
    private boolean inquiryReminders = true;

    @Column(name = "email_notifications")
    private boolean emailNotifications = false;

    public NotificationSetting() {}

    public NotificationSetting(User user, boolean meetingReminders, boolean inquiryReminders, boolean emailNotifications) {
        this.user = user;
        this.meetingReminders = meetingReminders;
        this.inquiryReminders = inquiryReminders;
        this.emailNotifications = emailNotifications;
    }

    // --- Getters and Setters ---
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public boolean isMeetingReminders() { return meetingReminders; }
    public void setMeetingReminders(boolean meetingReminders) { this.meetingReminders = meetingReminders; }

    public boolean isInquiryReminders() { return inquiryReminders; }
    public void setInquiryReminders(boolean inquiryReminders) { this.inquiryReminders = inquiryReminders; }

    public boolean isEmailNotifications() { return emailNotifications; }
    public void setEmailNotifications(boolean emailNotifications) { this.emailNotifications = emailNotifications; }
}