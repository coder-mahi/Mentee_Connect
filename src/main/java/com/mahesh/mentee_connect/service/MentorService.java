package com.mahesh.mentee_connect.service;

import com.mahesh.mentee_connect.model.Mentor;
import com.mahesh.mentee_connect.model.Response;
import com.mahesh.mentee_connect.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MentorService {

    @Autowired
    private MentorRepository mentorRepo;

    public List<Mentor> getAllMentors() {
        return mentorRepo.findAll();
    }

    public Mentor getMentorById(String id) {
        return mentorRepo.findById(id).orElse(null);
    }

    public Mentor addMentor(Mentor mentor) {
        return mentorRepo.save(mentor);
    }

    public Mentor updateMentor(String id, Mentor updatedMentor) {
        updatedMentor.setId(id);
        return mentorRepo.save(updatedMentor);
    }

    public void deleteMentor(String id) {
        mentorRepo.deleteById(id);
    }
    

}
