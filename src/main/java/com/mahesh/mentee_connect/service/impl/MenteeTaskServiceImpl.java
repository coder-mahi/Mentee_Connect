package com.mahesh.mentee_connect.service.impl;

import com.mahesh.mentee_connect.model.MenteeTask;
import com.mahesh.mentee_connect.repository.MenteeTaskRepository;
import com.mahesh.mentee_connect.service.MenteeTaskService;
import com.mahesh.mentee_connect.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MenteeTaskServiceImpl implements MenteeTaskService {

    @Autowired
    private MenteeTaskRepository menteeTaskRepository;

    @Override
    @Transactional
    public MenteeTask createTask(MenteeTask task) {
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());
        return menteeTaskRepository.save(task);
    }

    @Override
    @Transactional
    public MenteeTask updateTask(String taskId, MenteeTask taskDetails) {
        MenteeTask task = getTaskById(taskId);
        
        // Update fields
        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        task.setDueDate(taskDetails.getDueDate());
        task.setStatus(taskDetails.getStatus());
        task.setFeedback(taskDetails.getFeedback());
        task.setUpdatedAt(LocalDateTime.now());
        
        return menteeTaskRepository.save(task);
    }

    @Override
    @Transactional
    public void deleteTask(String taskId) {
        MenteeTask task = getTaskById(taskId);
        menteeTaskRepository.delete(task);
    }

    @Override
    @Transactional(readOnly = true)
    public MenteeTask getTaskById(String taskId) {
        return menteeTaskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", taskId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MenteeTask> getTasksByMentor(String mentorId) {
        return menteeTaskRepository.findByMentorId(mentorId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MenteeTask> getTasksByStudent(String studentId) {
        return menteeTaskRepository.findByStudentId(studentId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MenteeTask> getTasksByMentorAndStudent(String mentorId, String studentId) {
        return menteeTaskRepository.findByMentorIdAndStudentId(mentorId, studentId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MenteeTask> getOverdueTasks() {
        return menteeTaskRepository.findByDueDateBefore(LocalDate.now());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MenteeTask> getTasksByStatus(MenteeTask.TaskStatus status) {
        return menteeTaskRepository.findByStatus(status);
    }
} 