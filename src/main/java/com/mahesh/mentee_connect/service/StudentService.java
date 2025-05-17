package com.mahesh.mentee_connect.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import com.mahesh.mentee_connect.model.Meeting;
import com.mahesh.mentee_connect.model.Student;
import com.mahesh.mentee_connect.dto.MentorResponse;
import com.mahesh.mentee_connect.dto.ProgressReport;
import com.mahesh.mentee_connect.dto.StudentDTO;

public interface StudentService {
    Student createStudent(Student student);
    Student updateStudent(String id, Student studentDetails);
    void deleteStudent(String id);
    Student getStudentById(String id);
    Student getStudentByUsername(String username);
    List<Student> getAllStudents();
    Page<Student> getAllStudentsPageable(int page, int size);
    List<Student> getStudentsByMentorId(String mentorId);
    List<Meeting> getStudentMeetings(String studentId);
    Student assignMentorToStudent(String studentId, String mentorId);
    Student updateStudentProgress(String studentId, double attendance, double cgpa);
    Student getCurrentStudent();
    Student getCurrentStudent(Authentication authentication);
    MentorResponse getMentorResponseByStudentId(String studentId);
    MentorResponse getMyMentor(String studentId);
    Student getStudentByEmail(String email);
    List<Student> searchStudents(String query);
    List<ProgressReport> getProgressReports(String studentId);
    List<StudentDTO> getAllStudentsAsDTO();
}