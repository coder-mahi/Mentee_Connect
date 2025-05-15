package com.mahesh.mentee_connect.service;

import com.mahesh.mentee_connect.exception.ResourceNotFoundException;
import com.mahesh.mentee_connect.model.Mentor;
import com.mahesh.mentee_connect.model.Student;
import com.mahesh.mentee_connect.repository.MentorRepository;
import com.mahesh.mentee_connect.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private MentorRepository mentorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;  // Inject PasswordEncoder here

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(String id) {
        return studentRepository.findById(id).orElse(null);
    }

    public Student addStudent(Student student) {
        //Hash the password before saving
        if (student.getPassword() != null && !student.getPassword().isEmpty()) {
            student.setPassword(passwordEncoder.encode(student.getPassword()));
        }
        
        return studentRepository.save(student);
    }

    public Student updateStudent(String id, Student updated) {
        updated.setId(id);
        //hash the password while updating if password is changed
        if (updated.getPassword() != null && !updated.getPassword().isEmpty()) {
            updated.setPassword(passwordEncoder.encode(updated.getPassword()));
        }
        return studentRepository.save(updated);
    }

    public void deleteStudent(String id) {
        studentRepository.deleteById(id);
    }
    
    
    public boolean assignMentor(String studentEmail, String mentorName) {
        Optional<Student> studentOptional = studentRepository.findByEmail(studentEmail);

        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            student.setMentorName(mentorName);
            studentRepository.save(student);
            return true; // Success
        } else {
            return false; // Student not found
        }
    }
        
    public Mentor getMyMentor(String studentEmail) {
        Student student = studentRepository.findByEmail(studentEmail)
            .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        
        if (student.getMentorName() == null || student.getMentorName().isEmpty()) {
            throw new ResourceNotFoundException("No mentor assigned");
        }
        
        return mentorRepository.findByName(student.getMentorName())
            .orElseThrow(() -> new ResourceNotFoundException("Mentor not found"));
    }
}