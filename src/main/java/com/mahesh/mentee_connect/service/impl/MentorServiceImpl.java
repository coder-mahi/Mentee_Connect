package com.mahesh.mentee_connect.service.impl;

import com.mahesh.mentee_connect.model.Mentor;
import com.mahesh.mentee_connect.repository.MentorRepository;
import com.mahesh.mentee_connect.service.MentorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MentorServiceImpl implements MentorService {

    @Autowired
    private MentorRepository mentorRepository;

    @Override
    public Mentor saveMentor(Mentor mentor) {
        return mentorRepository.save(mentor);
    }

    @Override
    public List<Mentor> getAllMentors() {
        return mentorRepository.findAll();
    }

    @Override
    public Mentor getMentorById(Long id) {
        return mentorRepository.findById(id).orElse(null);
    }

    @Override
    public Mentor updateMentor(Long id, Mentor mentor) {
        mentor.setId(id);
        return mentorRepository.save(mentor);
    }

    @Override
    public void deleteMentor(Long id) {
        mentorRepository.deleteById(id);
    }
}

