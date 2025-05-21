package com.mahesh.mentee_connect.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "batches")
public class Batch {
    
    @Id
    private String id;
    private String batchName;
    private String course;
    private List<String> mentorsAssigned = new ArrayList<>(); // Mentors assigned to this batch
    private List<String> studentsAssigned = new ArrayList<>(); // Students assigned to this batch
    private LocalDate startDate;
    private LocalDate endDate;
    
    // Constructors, Getters and Setters
    public Batch() {
        // Default constructor
    }
    
    public Batch(String batchName, String course) {
        this.batchName = batchName;
        this.course = course;
    }
    
    // Constructor for backward compatibility
    public Batch(String batchName, String course, String mentorId) {
        this.batchName = batchName;
        this.course = course;
        if (mentorId != null) {
            this.mentorsAssigned.add(mentorId);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
    
    // For backward compatibility
    public String getMentorAssigned() {
        return !mentorsAssigned.isEmpty() ? mentorsAssigned.get(0) : null;
    }

    // For backward compatibility
    public void setMentorAssigned(String mentorId) {
        if (mentorId != null && !mentorsAssigned.contains(mentorId)) {
            this.mentorsAssigned.add(mentorId);
        }
    }
    
    public List<String> getMentorsAssigned() {
        return mentorsAssigned;
    }
    
    public void setMentorsAssigned(List<String> mentorsAssigned) {
        this.mentorsAssigned = mentorsAssigned != null ? mentorsAssigned : new ArrayList<>();
    }

    public List<String> getStudentsAssigned() {
        return studentsAssigned;
    }
    
    public void setStudentsAssigned(List<String> studentsAssigned) {
        this.studentsAssigned = studentsAssigned;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
