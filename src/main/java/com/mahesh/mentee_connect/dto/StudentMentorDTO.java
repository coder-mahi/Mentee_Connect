package com.mahesh.mentee_connect.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentMentorDTO {
    private String id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String studentId;
    private String course;
    private String batch;
    private int semester;
    private double attendance;
    private double cgpa;
    private String assignedMentorId; // Only include mentor ID, not the entire mentor object
} 