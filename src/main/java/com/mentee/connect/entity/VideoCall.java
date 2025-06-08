package com.mentee.connect.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "video_calls")
public class VideoCall {
    @Id
    private String id;
    private String meetingId;
    private String status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private List<Participant> participants;

    @Data
    public static class Participant {
        private String userId;
        private String name;
        private String role;
        private LocalDateTime joinedAt;
        private LocalDateTime leftAt;
    }
} 