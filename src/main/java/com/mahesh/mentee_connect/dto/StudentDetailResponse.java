package com.mahesh.mentee_connect.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentDetailResponse {
    private String id;
    private String name;
    private String email;
    private String batch;
    private String course;
} 