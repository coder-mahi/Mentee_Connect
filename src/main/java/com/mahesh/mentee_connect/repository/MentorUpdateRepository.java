package com.mahesh.mentee_connect.repository;

import com.mahesh.mentee_connect.model.MentorUpdate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;

public interface MentorUpdateRepository extends MongoRepository<MentorUpdate, String> {
    List<MentorUpdate> findByMentorId(String mentorId);
    
    // Find updates for a specific student
    @Query("{ 'studentIds': ?0 }")
    List<MentorUpdate> findByStudentId(String studentId);
    
    // Find important updates
    List<MentorUpdate> findByIsImportantTrue();
    
    // Find updates for a specific mentor that are meant for a specific student
    @Query("{ 'mentorId': ?0, 'studentIds': ?1 }")
    List<MentorUpdate> findByMentorIdAndStudentId(String mentorId, String studentId);
} 