package com.example.realestate.service;

import com.example.realestate.models.Inquiry;
import com.example.realestate.models.Notification;
import com.example.realestate.repositories.InquiryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class InquiryService {

    @Autowired
    private InquiryRepository inquiryRepository;

    @Autowired
    private NotificationService notificationService;

    public List<Inquiry> getAllInquiries() {
        return inquiryRepository.findAll();
    }

    public Inquiry createInquiry(Inquiry inquiry) {
        Inquiry saved = inquiryRepository.save(inquiry);

        // Error Fix: .getAgentId() වෙනුවට .getAgent().getId() භාවිතය
        notificationService.createNotification(
                inquiry.getAgent().getId(),
                Notification.NotificationType.NEW_INQUIRY,
                "New Property Inquiry",
                "A client has sent an inquiry regarding Property ID: " + inquiry.getPropertyId(),
                saved.getId()
        );

        return saved;
    }

    public Inquiry replyToInquiry(Long id, String replyMessage) {
        Inquiry inq = inquiryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inquiry not found"));

        inq.setReplied(true);
        inq.setRepliedAt(LocalDateTime.now());
        Inquiry saved = inquiryRepository.save(inq);

        // Error Fix: inq.getClientId() වෙනුවට inq.getClient().getId() භාවිතය
        notificationService.createNotification(
                inq.getClient().getId(),
                Notification.NotificationType.GENERAL,
                "Inquiry Replied",
                "The agent has replied to your inquiry: \"" + replyMessage + "\"",
                id
        );

        return saved;
    }
}