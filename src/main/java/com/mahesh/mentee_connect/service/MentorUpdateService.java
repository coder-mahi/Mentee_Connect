package com.mahesh.mentee_connect.service;

import com.mahesh.mentee_connect.model.MentorUpdate;
import java.util.List;

public interface MentorUpdateService {
    MentorUpdate createUpdate(MentorUpdate update);
    MentorUpdate updateUpdate(String updateId, MentorUpdate update);
    void deleteUpdate(String updateId);
    MentorUpdate getUpdateById(String updateId);
    List<MentorUpdate> getUpdatesByMentor(String mentorId);
    List<MentorUpdate> getUpdatesByStudent(String studentId);
    List<MentorUpdate> getImportantUpdates();
    List<MentorUpdate> getUpdatesByMentorAndStudent(String mentorId, String studentId);
} 