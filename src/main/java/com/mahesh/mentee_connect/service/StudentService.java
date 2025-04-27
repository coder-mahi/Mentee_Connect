package com.mahesh.mentee_connect.service;

import com.mahesh.mentee_connect.model.Response;
import com.mahesh.mentee_connect.model.Student;
import com.mahesh.mentee_connect.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository repository;

    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    public Student getStudentById(String id) {
        return repository.findById(id).orElse(null);
    }

    public Student addStudent(Student student) {
        return repository.save(student);
    }

    public Student updateStudent(String id, Student updated) {
        updated.setId(id);
        return repository.save(updated);
    }

    public void deleteStudent(String id) {
        repository.deleteById(id);
    }
    

}
