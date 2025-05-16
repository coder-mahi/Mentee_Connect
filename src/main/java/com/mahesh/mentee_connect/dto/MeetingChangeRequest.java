package com.mahesh.mentee_connect.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MeetingChangeRequest {
    private String reason;
    private LocalDateTime proposedDateTime;
    private String additionalNotes;
} 