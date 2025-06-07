package com.mahesh.mentee_connect.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.mahesh.mentee_connect.model.Certificate;

public interface CertificateRepository extends MongoRepository<Certificate, String> {
    List<Certificate> findByStudentId(String studentId);
    void deleteByIdAndStudentId(String id, String studentId);
} 