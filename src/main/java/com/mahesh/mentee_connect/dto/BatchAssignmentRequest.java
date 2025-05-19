package com.mahesh.mentee_connect.dto;

import java.util.List;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class BatchAssignmentRequest {
    
    @NotNull(message = "Batch ID is required")
    private String batchId;
    
    @NotEmpty(message = "Student IDs list cannot be empty")
    private List<String> studentIds;

    public BatchAssignmentRequest() {
    }

    public BatchAssignmentRequest(String batchId, List<String> studentIds) {
        this.batchId = batchId;
        this.studentIds = studentIds;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public List<String> getStudentIds() {
        return studentIds;
    }

    public void setStudentIds(List<String> studentIds) {
        this.studentIds = studentIds;
    }
} 