// MeetingRequest.java
package com.mahesh.mentee_connect.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MeetingRequest {
    @NotBlank
    private String mentorId;
    
    @NotBlank
    private String studentId;
    
    @NotBlank
    private String title;
    
    private String description;
    
    @NotNull
    private LocalDateTime meetingTime;
}