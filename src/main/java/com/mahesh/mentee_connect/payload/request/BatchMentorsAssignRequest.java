package com.mahesh.mentee_connect.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public class BatchMentorsAssignRequest {
    @NotBlank(message = "Batch ID is required")
    private String batchId;
    
    @NotEmpty(message = "Mentor IDs list cannot be empty")
    private List<String> mentorIds;
    
    // Constructors
    public BatchMentorsAssignRequest() {
    }
    
    public BatchMentorsAssignRequest(String batchId, List<String> mentorIds) {
        this.batchId = batchId;
        this.mentorIds = mentorIds;
    }
    
    // Getters and Setters
    public String getBatchId() {
        return batchId;
    }
    
    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }
    
    public List<String> getMentorIds() {
        return mentorIds;
    }
    
    public void setMentorIds(List<String> mentorIds) {
        this.mentorIds = mentorIds;
    }
} 