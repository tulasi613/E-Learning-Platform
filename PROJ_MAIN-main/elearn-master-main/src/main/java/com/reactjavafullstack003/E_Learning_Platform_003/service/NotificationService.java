package com.reactjavafullstack003.E_Learning_Platform_003.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reactjavafullstack003.E_Learning_Platform_003.model.Notification;
import com.reactjavafullstack003.E_Learning_Platform_003.repository.NotificationRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    /**
     * Fetch all notifications from the repository.
     */
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    /**
     * Save a new notification with the given description.
     */
    public Notification saveNotification(String description) {
        Notification notification = new Notification(description, LocalDateTime.now());
        return notificationRepository.save(notification);
    }

    /**
     * Fetch a notification by ID from the repository.
     */
    public Notification getNotificationById(int id) {
        return notificationRepository.findById(id).orElse(null);
    }

    /**
     * Delete a notification by ID from the repository.
     */
    public void deleteNotification(int id) {
        notificationRepository.deleteById(id);
    }
}