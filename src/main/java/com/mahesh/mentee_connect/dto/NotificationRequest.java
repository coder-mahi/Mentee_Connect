package com.mahesh.mentee_connect.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class NotificationRequest {
    
    @NotBlank(message = "Title is required")
    private String title;
    
    @NotBlank(message = "Message is required")
    private String message;
    
    @NotNull(message = "Recipient type is required")
    private String recipientType; // "STUDENT", "MENTOR", or "ALL"
    
    private String recipientId; // Optional, for specific recipient

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRecipientType() {
        return recipientType;
    }

    public void setRecipientType(String recipientType) {
        this.recipientType = recipientType;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }
} 