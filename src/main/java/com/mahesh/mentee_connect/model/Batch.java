package com.mahesh.mentee_connect.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "batches")
public class Batch {
    
    @Id
    private String id;
    private String batchName;
    private String course;
    private String mentorAssigned;
    
    // Constructors, Getters and Setters
    public Batch(String batchName, String course, String mentorAssigned) {
        this.batchName = batchName;
        this.course = course;
        this.mentorAssigned = mentorAssigned;
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
    }
}
