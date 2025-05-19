package com.mahesh.mentee_connect.controller;

import com.mahesh.mentee_connect.model.User;
import com.mahesh.mentee_connect.model.Student;
import com.mahesh.mentee_connect.model.Mentor;
import com.mahesh.mentee_connect.model.Admin;
import com.mahesh.mentee_connect.payload.response.UserResponse;
import com.mahesh.mentee_connect.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/profile")
@CrossOrigin(origins = "http://localhost:3000")
public class UserProfileController {
    private static final Logger logger = LoggerFactory.getLogger(UserProfileController.class);

    @Autowired
    private AuthService authService;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUserProfile() {
        logger.info("Fetching profile for current user");
        User currentUser = authService.getCurrentUser();
        
        if (currentUser == null) {
            logger.error("No authenticated user found");
            return ResponseEntity.badRequest().build();
        }

        UserResponse userResponse = convertToUserResponse(currentUser);
        return ResponseEntity.ok(userResponse);
    }

    private UserResponse convertToUserResponse(User user) {
        UserResponse.UserResponseBuilder builder = UserResponse.builder()
            .id(user.getId())
            .username(user.getUsername())
            .email(user.getEmail())
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .phoneNumber(user.getPhoneNumber())
            .role(user.getRole());

        if (user instanceof Student) {
            Student student = (Student) user;
            builder.specificId(student.getStudentId())
                   .additionalInfo(String.format("Course: %s, Semester: %d, Batch: %s", 
                                               student.getCourse(), 
                                               student.getSemester(), 
                                               student.getBatch()));
        } else if (user instanceof Mentor) {
            Mentor mentor = (Mentor) user;
            builder.specificId(mentor.getMentorId())
                   .additionalInfo(String.format("Department: %s", mentor.getDepartment()));
        } else if (user instanceof Admin) {
            Admin admin = (Admin) user;
            builder.specificId(admin.getAdminId())
                   .additionalInfo(String.format("Department: %s, Position: %s", 
                                               admin.getDepartment(), 
                                               admin.getPosition()));
        }

        return builder.build();
    }
}