package com.mahesh.mentee_connect.repository;

import com.mahesh.mentee_connect.model.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface NotificationRepository extends MongoRepository<Notification, String> {
    List<Notification> findByRecipientType(String recipientType);
    List<Notification> findByRecipientId(String recipientId);
    List<Notification> findByRecipientIdAndIsReadFalse(String recipientId);
    List<Notification> findByRecipientTypeOrRecipientId(String recipientType, String recipientId);
    List<Notification> findByRecipientTypeAndIsReadFalse(String recipientType);
} 