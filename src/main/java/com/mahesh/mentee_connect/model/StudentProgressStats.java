package com.mahesh.mentee_connect.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
public class StudentProgressStats {
    private String studentId;
    private String studentName;
    private List<Meeting> meetings;
    private String status;
    private double attendance;
    private double cgpa;
}