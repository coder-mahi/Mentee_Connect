package com.mahesh.mentee_connect.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ProgressReport {
    private String id;
    private LocalDateTime reportDate;
    private double attendance;
    private double cgpa;
    private String mentorComments;
    private String academicProgress;
} 