package com.mahesh.mentee_connect.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
public class MentorStats {
    private String mentorId;
    private String mentorName;
    private List<Meeting> meetings;
    private List<Student> students;
}