package com.mahesh.mentee_connect.service;

import com.mahesh.mentee_connect.model.Student;
import com.mahesh.mentee_connect.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;  // Inject PasswordEncoder here

    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    public Student getStudentById(String id) {
        return repository.findById(id).orElse(null);
    }

    public Student addStudent(Student student) {
        //Hash the password before saving
        if (student.getPassword() != null && !student.getPassword().isEmpty()) {
            student.setPassword(passwordEncoder.encode(student.getPassword()));
        }
        
        return repository.save(student);
    }

    public Student updateStudent(String id, Student updated) {
        updated.setId(id);
        //hash the password while updating if password is changed
        if (updated.getPassword() != null && !updated.getPassword().isEmpty()) {
            updated.setPassword(passwordEncoder.encode(updated.getPassword()));
        }
        return repository.save(updated);
    }

    public void deleteStudent(String id) {
        repository.deleteById(id);
    }
    
    
    public boolean assignMentor(String studentEmail, String mentorName) {
        Optional<Student> studentOptional = repository.findByEmail(studentEmail);

        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            student.setMentorName(mentorName);
            repository.save(student);
            return true; // Success
        } else {
            return false; // Student not found
        }
    }
        
}
