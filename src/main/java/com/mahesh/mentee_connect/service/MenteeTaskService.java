package com.mahesh.mentee_connect.service;

import com.mahesh.mentee_connect.model.MenteeTask;
import java.time.LocalDate;
import java.util.List;

public interface MenteeTaskService {
    MenteeTask createTask(MenteeTask task);
    MenteeTask updateTask(String taskId, MenteeTask task);
    void deleteTask(String taskId);
    MenteeTask getTaskById(String taskId);
    List<MenteeTask> getTasksByMentor(String mentorId);
    List<MenteeTask> getTasksByStudent(String studentId);
    List<MenteeTask> getTasksByMentorAndStudent(String mentorId, String studentId);
    List<MenteeTask> getOverdueTasks();
    List<MenteeTask> getTasksByStatus(MenteeTask.TaskStatus status);
} 