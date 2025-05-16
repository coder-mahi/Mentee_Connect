// Student.java
package com.mahesh.mentee_connect.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Document(collection = "students")
public class Student extends User {
    private String studentId;
    private String course;
    private String batch;
    private int semester;
    
    @DBRef
    private Mentor assignedMentor;
    
    @DBRef(lazy = true)
    private List<Meeting> meetings = new ArrayList<>();
    
    private double attendance;
    private double cgpa;
    
    public Student(String username, String email, String password, String firstName, 
                  String lastName, String phoneNumber, String studentId, 
                  String course, String batch, int semester) {
        super();
        this.setUsername(username);
        this.setEmail(email);
        this.setPassword(password);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setPhoneNumber(phoneNumber);
        this.setRole(UserRole.ROLE_STUDENT);
        this.studentId = studentId;
        this.course = course;
        this.batch = batch;
        this.semester = semester;
        this.attendance = 0.0;
        this.cgpa = 0.0;
    }
}