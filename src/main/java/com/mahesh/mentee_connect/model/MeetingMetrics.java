package com.mahesh.mentee_connect.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import java.util.Map;

@Data
@AllArgsConstructor
public class MeetingMetrics {
    private int totalMeetings;
    private int completedMeetings;
    private int pendingMeetings;
    private Map<String, Integer> meetingsPerMentor;
    private double averageDuration;
} 