package com.mahesh.mentee_connect.repository;

import com.mahesh.mentee_connect.model.Student;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface StudentRepository extends MongoRepository<Student, String> {
    Optional<Student> findByEmail(String email);
    List<Student> findByMentorName(String mentorName);
}
