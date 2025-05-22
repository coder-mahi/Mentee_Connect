package com.mahesh.mentee_connect.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Map;

public class MenteeUpdateRequest {
    
    @Size(min = 1, max = 50, message = "First name must be between 1 and 50 characters")
    private String firstName;
    
    @Size(min = 1, max = 50, message = "Last name must be between 1 and 50 characters")
    private String lastName;
    
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone number must be a valid number")
    private String phoneNumber;
    
    private String course;
    private Integer semester;
    private Double cgpa;
    private Map<String, String> additionalDetails;
    
    // Getters and setters
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public String getCourse() {
        return course;
    }
    
    public void setCourse(String course) {
        this.course = course;
    }
    
    public Integer getSemester() {
        return semester;
    }
    
    public void setSemester(Integer semester) {
        this.semester = semester;
    }
    
    public Double getCgpa() {
        return cgpa;
    }
    
    public void setCgpa(Double cgpa) {
        this.cgpa = cgpa;
    }
    
    public Map<String, String> getAdditionalDetails() {
        return additionalDetails;
    }
    
    public void setAdditionalDetails(Map<String, String> additionalDetails) {
        this.additionalDetails = additionalDetails;
    }
} 