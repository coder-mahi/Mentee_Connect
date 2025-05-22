package com.mahesh.mentee_connect.service.impl;

import com.mahesh.mentee_connect.model.MenteeAttendance;
import com.mahesh.mentee_connect.model.MenteeAttendance.AttendanceStatus;
import com.mahesh.mentee_connect.repository.MenteeAttendanceRepository;
import com.mahesh.mentee_connect.service.MenteeAttendanceService;
import com.mahesh.mentee_connect.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MenteeAttendanceServiceImpl implements MenteeAttendanceService {

    @Autowired
    private MenteeAttendanceRepository attendanceRepository;

    @Override
    @Transactional
    public MenteeAttendance recordAttendance(MenteeAttendance attendance) {
        attendance.setCreatedAt(LocalDateTime.now());
        attendance.setUpdatedAt(LocalDateTime.now());
        return attendanceRepository.save(attendance);
    }

    @Override
    @Transactional
    public MenteeAttendance updateAttendance(String attendanceId, MenteeAttendance attendanceDetails) {
        MenteeAttendance attendance = getAttendanceById(attendanceId);
        
        // Update fields
        attendance.setDate(attendanceDetails.getDate());
        attendance.setBatchId(attendanceDetails.getBatchId());
        attendance.setSessionTopic(attendanceDetails.getSessionTopic());
        attendance.setStudentAttendance(attendanceDetails.getStudentAttendance());
        attendance.setNotes(attendanceDetails.getNotes());
        attendance.setUpdatedAt(LocalDateTime.now());
        
        return attendanceRepository.save(attendance);
    }

    @Override
    @Transactional
    public void deleteAttendance(String attendanceId) {
        MenteeAttendance attendance = getAttendanceById(attendanceId);
        attendanceRepository.delete(attendance);
    }

    @Override
    @Transactional(readOnly = true)
    public MenteeAttendance getAttendanceById(String attendanceId) {
        return attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance", "id", attendanceId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MenteeAttendance> getAttendanceByMentor(String mentorId) {
        return attendanceRepository.findByMentorId(mentorId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MenteeAttendance> getAttendanceByStudent(String studentId) {
        return attendanceRepository.findByStudentId(studentId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MenteeAttendance> getAttendanceByMentorAndStudent(String mentorId, String studentId) {
        return attendanceRepository.findByMentorIdAndStudentId(mentorId, studentId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MenteeAttendance> getAttendanceByDateRange(LocalDate startDate, LocalDate endDate) {
        return attendanceRepository.findByDateBetween(startDate, endDate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MenteeAttendance> getAttendanceByMentorAndDateRange(String mentorId, LocalDate startDate, LocalDate endDate) {
        return attendanceRepository.findByMentorIdAndDateBetween(mentorId, startDate, endDate);
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Double> calculateAttendancePercentage(String mentorId, String studentId, LocalDate startDate, LocalDate endDate) {
        List<MenteeAttendance> attendanceRecords = attendanceRepository.findByMentorIdAndDateBetween(mentorId, startDate, endDate);
        
        int totalClasses = 0;
        int presentClasses = 0;
        int absentClasses = 0;
        int lateClasses = 0;
        int excusedClasses = 0;
        
        for (MenteeAttendance record : attendanceRecords) {
            AttendanceStatus status = record.getStudentStatus(studentId);
            if (status != null) {
                totalClasses++;
                
                switch (status) {
                    case PRESENT:
                        presentClasses++;
                        break;
                    case ABSENT:
                        absentClasses++;
                        break;
                    case LATE:
                        lateClasses++;
                        break;
                    case EXCUSED:
                        excusedClasses++;
                        break;
                }
            }
        }
        
        Map<String, Double> result = new HashMap<>();
        if (totalClasses > 0) {
            result.put("presentPercentage", (double) presentClasses / totalClasses * 100);
            result.put("absentPercentage", (double) absentClasses / totalClasses * 100);
            result.put("latePercentage", (double) lateClasses / totalClasses * 100);
            result.put("excusedPercentage", (double) excusedClasses / totalClasses * 100);
        } else {
            result.put("presentPercentage", 0.0);
            result.put("absentPercentage", 0.0);
            result.put("latePercentage", 0.0);
            result.put("excusedPercentage", 0.0);
        }
        
        result.put("totalClasses", (double) totalClasses);
        
        return result;
    }
} 