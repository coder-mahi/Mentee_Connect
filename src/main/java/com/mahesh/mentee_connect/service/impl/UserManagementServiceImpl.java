package com.mahesh.mentee_connect.service.impl;

import com.mahesh.mentee_connect.model.User;
import com.mahesh.mentee_connect.model.Student;
import com.mahesh.mentee_connect.model.Mentor;
import com.mahesh.mentee_connect.model.Admin;
import com.mahesh.mentee_connect.repository.StudentRepository;
import com.mahesh.mentee_connect.repository.MentorRepository;
import com.mahesh.mentee_connect.repository.AdminRepository;
import com.mahesh.mentee_connect.service.UserManagementService;
import com.mahesh.mentee_connect.payload.request.UpdateProfileRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserManagementServiceImpl implements UserManagementService {
    private static final Logger logger = LoggerFactory.getLogger(UserManagementServiceImpl.class);

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private MentorRepository mentorRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        allUsers.addAll(studentRepository.findAll());
        allUsers.addAll(mentorRepository.findAll());
        allUsers.addAll(adminRepository.findAll());
        return allUsers;
    }

    @Override
    public List<User> searchUsersByName(String searchTerm) {
        List<User> matchingUsers = new ArrayList<>();
        String searchTermLower = searchTerm.toLowerCase();
        
        // Search in all repositories
        matchingUsers.addAll(studentRepository.findByFirstNameContainingOrLastNameContainingOrEmailContaining(
            searchTerm, searchTerm, searchTerm));
        matchingUsers.addAll(mentorRepository.findByFirstNameContainingOrLastNameContainingOrEmailContaining(
            searchTerm, searchTerm, searchTerm));
        matchingUsers.addAll(adminRepository.findByFirstNameContainingOrLastNameContainingOrEmailContaining(
            searchTerm, searchTerm, searchTerm));
        
        return matchingUsers;
    }

    @Override
    @Transactional
    public void deleteUser(String userId) {
        if (studentRepository.existsById(userId)) {
            studentRepository.deleteById(userId);
        } else if (mentorRepository.existsById(userId)) {
            mentorRepository.deleteById(userId);
        } else if (adminRepository.existsById(userId)) {
            adminRepository.deleteById(userId);
        } else {
            throw new UsernameNotFoundException("User not found with id: " + userId);
        }
    }

    @Override
    @Transactional
    public User updateUser(String userId, UpdateProfileRequest updateRequest) {
        // Try to find and update the user in the appropriate repository
        if (studentRepository.existsById(userId)) {
            Student student = studentRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("Student not found"));
            
            updateCommonFields(student, updateRequest);
            if (updateRequest.getCourse() != null) {
                student.setCourse(updateRequest.getCourse());
            }
            if (updateRequest.getSemester() != null) {
                student.setSemester(updateRequest.getSemester());
            }
            if (updateRequest.getBatch() != null) {
                student.setBatch(updateRequest.getBatch());
            }
            
            return studentRepository.save(student);
            
        } else if (mentorRepository.existsById(userId)) {
            Mentor mentor = mentorRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("Mentor not found"));
            
            updateCommonFields(mentor, updateRequest);
            if (updateRequest.getDepartment() != null) {
                mentor.setDepartment(updateRequest.getDepartment());
            }
            
            return mentorRepository.save(mentor);
            
        } else if (adminRepository.existsById(userId)) {
            Admin admin = adminRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("Admin not found"));
            
            updateCommonFields(admin, updateRequest);
            if (updateRequest.getDepartment() != null) {
                admin.setDepartment(updateRequest.getDepartment());
            }
            if (updateRequest.getPosition() != null) {
                admin.setPosition(updateRequest.getPosition());
            }
            
            return adminRepository.save(admin);
        }
        
        throw new UsernameNotFoundException("User not found with id: " + userId);
    }

    @Override
    public boolean isEmailTaken(String email) {
        return studentRepository.findByEmail(email).isPresent() ||
               mentorRepository.findByEmail(email).isPresent() ||
               adminRepository.findByEmail(email).isPresent();
    }

    private void updateCommonFields(User user, UpdateProfileRequest updateRequest) {
        user.setFirstName(updateRequest.getFirstName());
        user.setLastName(updateRequest.getLastName());
        user.setEmail(updateRequest.getEmail());
        user.setPhoneNumber(updateRequest.getPhoneNumber());
    }
} 