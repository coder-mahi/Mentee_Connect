package com.mahesh.mentee_connect.security.services;

import com.mahesh.mentee_connect.model.MenteeTask;
import com.mahesh.mentee_connect.model.MentorUpdate;
import com.mahesh.mentee_connect.model.MenteeAttendance;
import com.mahesh.mentee_connect.repository.MenteeTaskRepository;
import com.mahesh.mentee_connect.repository.MentorUpdateRepository;
import com.mahesh.mentee_connect.repository.MenteeAttendanceRepository;
import com.mahesh.mentee_connect.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("securityService")
public class SecurityService {

    @Autowired
    private AuthService authService;
    
    @Autowired
    private MenteeTaskRepository menteeTaskRepository;
    
    @Autowired
    private MentorUpdateRepository mentorUpdateRepository;
    
    @Autowired
    private MenteeAttendanceRepository menteeAttendanceRepository;

    /**
     * Check if the current authenticated user has the ID provided
     */
    public boolean isCurrentUser(String userId) {
        String currentUserId = authService.getCurrentUserId();
        return currentUserId != null && currentUserId.equals(userId);
    }
    
    /**
     * Check if the current authenticated user is the owner of a task
     */
    public boolean isTaskOwner(String taskId) {
        String currentUserId = authService.getCurrentUserId();
        if (currentUserId == null) {
            return false;
        }
        
        return menteeTaskRepository.findById(taskId)
                .map(task -> currentUserId.equals(task.getMentorId()))
                .orElse(false);
    }
    
    /**
     * Check if the current authenticated user is the owner of an update
     */
    public boolean isUpdateOwner(String updateId) {
        String currentUserId = authService.getCurrentUserId();
        if (currentUserId == null) {
            return false;
        }
        
        return mentorUpdateRepository.findById(updateId)
                .map(update -> currentUserId.equals(update.getMentorId()))
                .orElse(false);
    }
    
    /**
     * Check if the current authenticated user is the owner of an attendance record
     */
    public boolean isAttendanceOwner(String attendanceId) {
        String currentUserId = authService.getCurrentUserId();
        if (currentUserId == null) {
            return false;
        }
        
        return menteeAttendanceRepository.findById(attendanceId)
                .map(attendance -> currentUserId.equals(attendance.getMentorId()))
                .orElse(false);
    }
} 