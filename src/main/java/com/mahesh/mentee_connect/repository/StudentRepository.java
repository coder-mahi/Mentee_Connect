package com.mahesh.mentee_connect.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.mahesh.mentee_connect.model.*;
public interface StudentRepository extends JpaRepository<Student, Long> {}
