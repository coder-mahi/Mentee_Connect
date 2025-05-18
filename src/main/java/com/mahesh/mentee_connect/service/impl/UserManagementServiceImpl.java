package com.mahesh.mentee_connect.service.impl;

import com.mahesh.mentee_connect.model.User;
import com.mahesh.mentee_connect.model.Student;
import com.mahesh.mentee_connect.model.Mentor;
import com.mahesh.mentee_connect.model.Admin;
import com.mahesh.mentee_connect.repository.StudentRepository;
import com.mahesh.mentee_connect.repository.MentorRepository;
import com.mahesh.mentee_connect.repository.AdminRepository;
import com.mahesh.mentee_connect.service.UserManagementService;
import com.mahesh.mentee_connect.payload.request.UpdateUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserManagementServiceImpl implements UserManagementService {

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
        
        // Search in students
        matchingUsers.addAll(studentRepository.findByFirstNameContainingOrLastNameContainingOrEmailContaining(
            searchTerm, searchTerm, searchTerm));
        
        // Search in mentors
        matchingUsers.addAll(mentorRepository.findAll().stream()
            .filter(mentor -> 
                mentor.getFirstName().toLowerCase().contains(searchTerm.toLowerCase()) ||
                mentor.getLastName().toLowerCase().contains(searchTerm.toLowerCase()) ||
                mentor.getEmail().toLowerCase().contains(searchTerm.toLowerCase()))
            .collect(Collectors.toList()));
        
        // Search in admins
        matchingUsers.addAll(adminRepository.findAll().stream()
            .filter(admin -> 
                admin.getFirstName().toLowerCase().contains(searchTerm.toLowerCase()) ||
                admin.getLastName().toLowerCase().contains(searchTerm.toLowerCase()) ||
                admin.getEmail().toLowerCase().contains(searchTerm.toLowerCase()))
            .collect(Collectors.toList()));
        
        return matchingUsers;
    }

    @Override
    @Transactional
    public void deleteUser(String userId) {
        // Try to delete from each repository
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
    public User updateUser(String userId, UpdateUserRequest updateRequest) {
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
            if (updateRequest.getSpecialization() != null) {
                mentor.setSpecialization(updateRequest.getSpecialization());
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

    private void updateCommonFields(User user, UpdateUserRequest updateRequest) {
        user.setFirstName(updateRequest.getFirstName());
        user.setLastName(updateRequest.getLastName());
        user.setEmail(updateRequest.getEmail());
        user.setPhoneNumber(updateRequest.getPhoneNumber());
    }
} 