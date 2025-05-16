package com.mahesh.mentee_connect.service.impl;

import com.mahesh.mentee_connect.model.Student;
import com.mahesh.mentee_connect.model.Mentor;
import com.mahesh.mentee_connect.model.Meeting;
import com.mahesh.mentee_connect.dto.MentorResponse;
import com.mahesh.mentee_connect.dto.ProgressReport;
import com.mahesh.mentee_connect.repository.StudentRepository;
import com.mahesh.mentee_connect.repository.MentorRepository;
import com.mahesh.mentee_connect.repository.MeetingRepository;
import com.mahesh.mentee_connect.service.StudentService;
import com.mahesh.mentee_connect.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.time.LocalDateTime;

@Service
public class StudentServiceImpl implements StudentService {
    
    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private MentorRepository mentorRepository;
    
    @Autowired
    private MeetingRepository meetingRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Student createStudent(Student student) {
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        return studentRepository.save(student);
    }

    @Override
    @Transactional
    public Student updateStudent(String id, Student studentDetails) {
        Student student = getStudentById(id);
        student.setFirstName(studentDetails.getFirstName());
        student.setLastName(studentDetails.getLastName());
        student.setEmail(studentDetails.getEmail());
        student.setPhoneNumber(studentDetails.getPhoneNumber());
        student.setCourse(studentDetails.getCourse());
        student.setBatch(studentDetails.getBatch());
        student.setSemester(studentDetails.getSemester());
        return studentRepository.save(student);
    }

    @Override
    @Transactional
    public void deleteStudent(String id) {
        Student student = getStudentById(id);
        studentRepository.delete(student);
    }

    @Override
    @Transactional(readOnly = true)
    public Student getStudentById(String id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", id));
    }

    @Override
    @Transactional(readOnly = true)
    public Student getStudentByUsername(String username) {
        return studentRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "username", username));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> getStudentsByMentorId(String mentorId) {
        return studentRepository.findByAssignedMentorId(mentorId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Meeting> getStudentMeetings(String studentId) {
        return meetingRepository.findByStudentId(studentId);
    }

    @Override
    @Transactional
    public Student assignMentorToStudent(String studentId, String mentorId) {
        Student student = getStudentById(studentId);
        Mentor mentor = mentorRepository.findById(mentorId)
                .orElseThrow(() -> new ResourceNotFoundException("Mentor", "id", mentorId));
        
        student.setAssignedMentor(mentor);
        return studentRepository.save(student);
    }

    @Override
    @Transactional
    public Student updateStudentProgress(String studentId, double attendance, double cgpa) {
        Student student = getStudentById(studentId);
        student.setAttendance(attendance);
        student.setCgpa(cgpa);
        return studentRepository.save(student);
    }

    @Override
    @Transactional(readOnly = true)
    public Student getCurrentStudent() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return getCurrentStudent(auth);
    }

    @Override
    @Transactional(readOnly = true)
    public Student getCurrentStudent(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        String username = authentication.getName();
        return studentRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "username", username));
    }

    @Override
    @Transactional(readOnly = true)
    public Student getStudentByEmail(String email) {
        return studentRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "email", email));
    }

    @Override
    @Transactional(readOnly = true)
    public MentorResponse getMentorResponseByStudentId(String studentId) {
        Student student = getStudentById(studentId);
        if (student.getAssignedMentor() == null) {
            throw new ResourceNotFoundException("Mentor not assigned to student", "studentId", studentId);
        }
        Mentor mentor = student.getAssignedMentor();
        return new MentorResponse(mentor.getId(), mentor.getFirstName(), mentor.getLastName(), 
            mentor.getEmail(), mentor.getPhoneNumber(), mentor.getDepartment(), mentor.getSpecialization());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Student> getAllStudents(int page, int size) {
        return studentRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> searchStudents(String query) {
        return studentRepository.findByFirstNameContainingOrLastNameContainingOrEmailContaining(
            query, query, query);
    }

    @Override
    @Transactional(readOnly = true)
    public MentorResponse getMyMentor(String studentId) {
        Student student = getStudentById(studentId);
        if (student.getAssignedMentor() == null) {
            throw new ResourceNotFoundException("Mentor not assigned to student", "studentId", studentId);
        }
        Mentor mentor = student.getAssignedMentor();
        return new MentorResponse(mentor.getId(), mentor.getFirstName(), mentor.getLastName(),
            mentor.getEmail(), mentor.getPhoneNumber(), mentor.getDepartment(), mentor.getSpecialization());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProgressReport> getProgressReports(String studentId) {
        Student student = getStudentById(studentId);
        // TODO: Implement actual progress report retrieval logic
        // This is a placeholder implementation
        ProgressReport report = new ProgressReport();
        report.setId(studentId);
        report.setReportDate(LocalDateTime.now());
        report.setAttendance(student.getAttendance());
        report.setCgpa(student.getCgpa());
        return List.of(report);
    }
} 