package com.mahesh.mentee_connect.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserRequest {
    @NotBlank(message = "First name is required")
    @Size(max = 50)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 50)
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Size(max = 15)
    private String phoneNumber;

    // Optional fields specific to user types
    private String department;    // For Mentor and Admin
    private String course;        // For Student
    private String position;      // For Admin
    private String specialization; // For Mentor
    private Integer semester;      // For Student
    private String batch;         // For Student
} 