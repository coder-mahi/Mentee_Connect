package com.mentee.connect.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "meetings")
public class Meeting {
    @Id
    private String id;
    private String title;
    private String description;
    private LocalDateTime scheduledTime;
    private String status;
    private String mentorId;
    private List<String> mentees;
    private String meetingLink;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;
    private Integer duration;
} 