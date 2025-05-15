package com.mahesh.mentee_connect.service;

import com.mahesh.mentee_connect.exception.ResourceNotFoundException;
import com.mahesh.mentee_connect.model.Mentor;
import com.mahesh.mentee_connect.model.Student;
import com.mahesh.mentee_connect.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MentorService {
    @Autowired
    private MentorRepository mentorRepository;
    
    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;


    public List<Mentor> getAllMentors() {
        return mentorRepository.findAll();
    }

    public Mentor getMentorById(String id) {
        return mentorRepository.findById(id).orElse(null);
    }

    public Mentor addMentor(Mentor mentor) {
    	
    	 if (mentor.getPassword() != null && !mentor.getPassword().isEmpty()) {
    		 mentor.setPassword(passwordEncoder.encode(mentor.getPassword()));
         }
    	 
         return mentorRepository.save(mentor);
    }

    public Mentor updateMentor(String id, Mentor updatedMentor) {
        updatedMentor.setId(id);
        
        if (updatedMentor.getPassword() != null && !updatedMentor.getPassword().isEmpty()) {
        	updatedMentor.setPassword(passwordEncoder.encode(updatedMentor.getPassword()));
        }
        return mentorRepository.save(updatedMentor);
    }

    public void deleteMentor(String id) {
        mentorRepository.deleteById(id);
    }
    

    public List<Student> getMyMentees() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String mentorEmail = authentication.getName();
        
        // Get mentor by email to ensure they exist
        Mentor mentor = mentorRepository.findByEmail(mentorEmail)
            .orElseThrow(() -> new ResourceNotFoundException("Mentor not found"));
        
        // Find students with this mentor's name
        return studentRepository.findByMentorName(mentor.getName());
    }
}
