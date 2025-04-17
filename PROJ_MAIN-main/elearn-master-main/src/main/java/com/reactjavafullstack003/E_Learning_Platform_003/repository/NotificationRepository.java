package com.reactjavafullstack003.E_Learning_Platform_003.repository;


import com.reactjavafullstack003.E_Learning_Platform_003.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    // JpaRepository provides CRUD operations for Notification entity
}