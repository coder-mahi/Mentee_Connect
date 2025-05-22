package com.mahesh.mentee_connect.service;

import com.mahesh.mentee_connect.model.Mentor;
import com.mahesh.mentee_connect.model.Student;
import com.mahesh.mentee_connect.model.Meeting;
import com.mahesh.mentee_connect.dto.MenteeUpdateRequest;
import org.springframework.data.domain.Page;
import java.util.List;

public interface MentorService {
    Mentor createMentor(Mentor mentor);
    Mentor updateMentor(String id, Mentor mentor);
    void deleteMentor(String id);
    Mentor getMentorById(String id);
    Mentor getMentorByUsername(String username);
    List<Mentor> getAllMentors();
    List<Student> getMentorStudents(String mentorId);
    List<Meeting> getMentorMeetings(String mentorId);
    Mentor getCurrentMentor();
    boolean hasAvailableSlots(String mentorId);
    int getAssignedStudentsCount(String mentorId);
    Page<Mentor> getAllMentors(int page, int size);
    
    // New method for updating student information by mentor
    Student updateStudentAsMentor(String mentorId, String studentId, MenteeUpdateRequest updateRequest);
} 