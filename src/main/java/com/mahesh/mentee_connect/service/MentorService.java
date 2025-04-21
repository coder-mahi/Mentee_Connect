package com.mahesh.mentee_connect.service;

import com.mahesh.mentee_connect.model.*;
import java.util.List;

public interface MentorService {
    Mentor saveMentor(Mentor mentor);
    List<Mentor> getAllMentors();
    Mentor getMentorById(Long id);
}