package com.mahesh.mentee_connect.service;

import com.mahesh.mentee_connect.model.Mentor;
import java.util.List;

public interface MentorService {
    Mentor saveMentor(Mentor mentor);
    List<Mentor> getAllMentors();
    Mentor getMentorById(Long id);
    Mentor updateMentor(Long id, Mentor mentor);
    void deleteMentor(Long id);
}