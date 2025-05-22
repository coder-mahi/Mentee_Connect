package com.mahesh.mentee_connect.service;

import com.mahesh.mentee_connect.model.MenteeAttendance;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface MenteeAttendanceService {
    MenteeAttendance recordAttendance(MenteeAttendance attendance);
    MenteeAttendance updateAttendance(String attendanceId, MenteeAttendance attendance);
    void deleteAttendance(String attendanceId);
    MenteeAttendance getAttendanceById(String attendanceId);
    List<MenteeAttendance> getAttendanceByMentor(String mentorId);
    List<MenteeAttendance> getAttendanceByStudent(String studentId);
    List<MenteeAttendance> getAttendanceByMentorAndStudent(String mentorId, String studentId);
    List<MenteeAttendance> getAttendanceByDateRange(LocalDate startDate, LocalDate endDate);
    List<MenteeAttendance> getAttendanceByMentorAndDateRange(String mentorId, LocalDate startDate, LocalDate endDate);
    Map<String, Double> calculateAttendancePercentage(String mentorId, String studentId, LocalDate startDate, LocalDate endDate);
} 