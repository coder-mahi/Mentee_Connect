package com.mahesh.mentee_connect.payload.response;

import com.mahesh.mentee_connect.model.User.UserRole;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private UserRole role;
    private String specificId; // studentId, mentorId, or adminId
    private String additionalInfo; // department, course, or position based on role
} 