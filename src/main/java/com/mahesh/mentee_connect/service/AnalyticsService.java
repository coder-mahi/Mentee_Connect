package com.mahesh.mentee_connect.service;
import java.util.List;
import java.util.stream.Collectors;
import java.time.Duration;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mahesh.mentee_connect.model.Meeting;
import com.mahesh.mentee_connect.model.MeetingMetrics;
import com.mahesh.mentee_connect.model.Mentor;
import com.mahesh.mentee_connect.model.MentorStats;
import com.mahesh.mentee_connect.model.MentorEngagementStats;
import com.mahesh.mentee_connect.model.StudentProgressStats;
import com.mahesh.mentee_connect.repository.AdminRepository;
import com.mahesh.mentee_connect.repository.MeetingRepository;
import com.mahesh.mentee_connect.repository.MentorRepository;
import com.mahesh.mentee_connect.repository.StudentRepository;
@Service
public class AnalyticsService {

    @Autowired
    private MeetingRepository meetingRepository;
    @Autowired
    private MentorRepository mentorRepository;
    @Autowired
    private StudentRepository studentRepository;

    @Transactional(readOnly = true)
    public MentorEngagementStats getMentorEngagementStats() {
        List<Mentor> mentors = mentorRepository.findAll();
        return new MentorEngagementStats(
            mentors.stream()
                .map(mentor -> new MentorStats(
                    mentor.getId(),
                    mentor.getFirstName() + " " + mentor.getLastName(),
                    meetingRepository.findByMentorId(mentor.getId()),
                    studentRepository.findByAssignedMentorId(mentor.getId())
                ))
                .collect(Collectors.toList())
        );
    }

    @Transactional(readOnly = true)
    public List<StudentProgressStats> getStudentProgressStats() {
        return studentRepository.findAll().stream()
            .map(student -> new StudentProgressStats(
                student.getId(),
                student.getFirstName() + " " + student.getLastName(),
                meetingRepository.findByStudentId(student.getId()),
                student.getAssignedMentor() != null ? "ACTIVE" : "INACTIVE",
                student.getAttendance(),
                student.getCgpa()
            ))
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MeetingMetrics getMeetingMetrics(String timeRange) {
        List<Meeting> meetings = meetingRepository.findAll();
        
        int total = meetings.size();
        int completed = (int) meetings.stream()
            .filter(m -> m.getStatus() == Meeting.MeetingStatus.COMPLETED)
            .count();
        
        Map<String, Integer> perMentor = meetings.stream()
            .collect(Collectors.groupingBy(
                m -> m.getMentor().getId(),
                Collectors.collectingAndThen(Collectors.toList(), List::size)
            ));
            
        return new MeetingMetrics(
            total,
            completed,
            total - completed,
            perMentor,
            meetings.stream().mapToLong(m -> 
                m.getEndTime() != null && m.getScheduledTime() != null ? 
                Duration.between(m.getScheduledTime(), m.getEndTime()).toMinutes() : 0
            ).average().orElse(0.0)
        );
    }
}