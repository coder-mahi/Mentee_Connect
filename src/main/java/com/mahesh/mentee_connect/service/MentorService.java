package com.mahesh.mentee_connect.service;

import com.mahesh.mentee_connect.model.Mentor;
import com.mahesh.mentee_connect.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MentorService {
    @Autowired
    private MentorRepository mentorRepo;
    
    @Autowired
    private PasswordEncoder passwordEncoder;


    public List<Mentor> getAllMentors() {
        return mentorRepo.findAll();
    }

    public Mentor getMentorById(String id) {
        return mentorRepo.findById(id).orElse(null);
    }

    public Mentor addMentor(Mentor mentor) {
    	
    	 if (mentor.getPassword() != null && !mentor.getPassword().isEmpty()) {
    		 mentor.setPassword(passwordEncoder.encode(mentor.getPassword()));
         }
    	 
         return mentorRepo.save(mentor);
    }

    public Mentor updateMentor(String id, Mentor updatedMentor) {
        updatedMentor.setId(id);
        
        if (updatedMentor.getPassword() != null && !updatedMentor.getPassword().isEmpty()) {
        	updatedMentor.setPassword(passwordEncoder.encode(updatedMentor.getPassword()));
        }
        return mentorRepo.save(updatedMentor);
    }

    public void deleteMentor(String id) {
        mentorRepo.deleteById(id);
    }
    

}
