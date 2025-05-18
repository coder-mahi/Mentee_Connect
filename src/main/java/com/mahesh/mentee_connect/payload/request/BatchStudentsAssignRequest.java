package com.mahesh.mentee_connect.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import java.util.List;

@Data
public class BatchStudentsAssignRequest {
    @NotBlank
    private String batchId;
    
    @NotEmpty
    private List<String> studentIds;
} 