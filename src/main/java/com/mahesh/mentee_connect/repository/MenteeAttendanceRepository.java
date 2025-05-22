package com.mahesh.mentee_connect.repository;

import com.mahesh.mentee_connect.model.MenteeAttendance;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.time.LocalDate;
import java.util.List;

public interface MenteeAttendanceRepository extends MongoRepository<MenteeAttendance, String> {
    List<MenteeAttendance> findByMentorId(String mentorId);
    List<MenteeAttendance> findByBatchId(String batchId);
    List<MenteeAttendance> findByDate(LocalDate date);
    List<MenteeAttendance> findByDateBetween(LocalDate startDate, LocalDate endDate);
    List<MenteeAttendance> findByMentorIdAndDateBetween(String mentorId, LocalDate startDate, LocalDate endDate);
    
    // Find attendance records where a specific student is marked
    @Query("{ 'studentAttendance.?0': { $exists: true } }")
    List<MenteeAttendance> findByStudentId(String studentId);
    
    // Find attendance records for a specific mentor and student
    @Query("{ 'mentorId': ?0, 'studentAttendance.?1': { $exists: true } }")
    List<MenteeAttendance> findByMentorIdAndStudentId(String mentorId, String studentId);
} 