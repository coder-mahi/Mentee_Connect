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
    private String mentorAssigned; // Keeping for backward compatibility
    private List<String> mentorsAssigned = new ArrayList<>(); // New field for multiple mentors
    private LocalDate startDate;
    private LocalDate endDate;
    
    // Constructors, Getters and Setters
    public Batch() {
        // Default constructor
    }
    
    public Batch(String batchName, String course, String mentorAssigned) {
        this.batchName = batchName;
        this.course = course;
        this.mentorAssigned = mentorAssigned;
        if (mentorAssigned != null) {
            this.mentorsAssigned.add(mentorAssigned);
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

    public String getMentorAssigned() {
        return mentorAssigned;
    }

    public void setMentorAssigned(String mentorAssigned) {
        this.mentorAssigned = mentorAssigned;
        // Also update the list for new code
        if (mentorAssigned != null && !mentorsAssigned.contains(mentorAssigned)) {
            this.mentorsAssigned.add(mentorAssigned);
        }
    }
    
    public List<String> getMentorsAssigned() {
        return mentorsAssigned;
    }
    
    public void setMentorsAssigned(List<String> mentorsAssigned) {
        this.mentorsAssigned = mentorsAssigned;
        // Update the single mentor field for backward compatibility
        if (mentorsAssigned != null && !mentorsAssigned.isEmpty()) {
            this.mentorAssigned = mentorsAssigned.get(0);
        }
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
