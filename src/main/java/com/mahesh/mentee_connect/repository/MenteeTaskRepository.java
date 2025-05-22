package com.mahesh.mentee_connect.repository;

import com.mahesh.mentee_connect.model.MenteeTask;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.time.LocalDate;
import java.util.List;

public interface MenteeTaskRepository extends MongoRepository<MenteeTask, String> {
    List<MenteeTask> findByMentorId(String mentorId);
    List<MenteeTask> findByStudentId(String studentId);
    List<MenteeTask> findByMentorIdAndStudentId(String mentorId, String studentId);
    List<MenteeTask> findByDueDateBefore(LocalDate date);
    List<MenteeTask> findByStatus(MenteeTask.TaskStatus status);
} 