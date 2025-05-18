package com.mahesh.mentee_connect.service.impl;

import com.mahesh.mentee_connect.model.*;
import com.mahesh.mentee_connect.repository.*;
import com.mahesh.mentee_connect.service.AnalyticsService;
import com.mahesh.mentee_connect.payload.response.AnalyticsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.stream.Collectors;
import java.time.Duration;

@Service
public class AnalyticsServiceImpl implements AnalyticsService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private MentorRepository mentorRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private MeetingRepository meetingRepository;

    @Override
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

    @Override
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

    @Override
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

    @Override
    public AnalyticsResponse.UserCountStats getUserCountStats() {
        List<Student> students = studentRepository.findAll();
        List<Mentor> mentors = mentorRepository.findAll();
        List<Admin> admins = adminRepository.findAll();

        long activeStudents = students.stream().filter(Student::isActive).count();
        long activeMentors = mentors.stream().filter(Mentor::isActive).count();
        long activeAdmins = admins.stream().filter(Admin::isActive).count();

        return AnalyticsResponse.UserCountStats.builder()
                .totalUsers(students.size() + mentors.size() + admins.size())
                .totalStudents(students.size())
                .totalMentors(mentors.size())
                .totalAdmins(admins.size())
                .activeUsers(activeStudents + activeMentors + activeAdmins)
                .inactiveUsers((students.size() + mentors.size() + admins.size()) - 
                             (activeStudents + activeMentors + activeAdmins))
                .build();
    }

    @Override
    public AnalyticsResponse.DepartmentStats getDepartmentStats() {
        List<Student> students = studentRepository.findAll();
        List<Mentor> mentors = mentorRepository.findAll();

        // Students per department
        Map<String, Long> studentsPerDepartment = students.stream()
                .collect(Collectors.groupingBy(
                    student -> student.getCourse(),
                    Collectors.counting()
                ));

        // Mentors per department
        Map<String, Long> mentorsPerDepartment = mentors.stream()
                .collect(Collectors.groupingBy(
                    Mentor::getDepartment,
                    Collectors.counting()
                ));

        // Average CGPA by department
        Map<String, Double> averageCgpaByDepartment = students.stream()
                .collect(Collectors.groupingBy(
                    Student::getCourse,
                    Collectors.averagingDouble(Student::getCgpa)
                ));

        // Top performing departments (based on average CGPA)
        List<String> topPerformingDepartments = averageCgpaByDepartment.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .limit(5)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        return AnalyticsResponse.DepartmentStats.builder()
                .studentsPerDepartment(studentsPerDepartment)
                .mentorsPerDepartment(mentorsPerDepartment)
                .averageCgpaByDepartment(averageCgpaByDepartment)
                .topPerformingDepartments(topPerformingDepartments)
                .build();
    }

    @Override
    public AnalyticsResponse.StudentStats getStudentStats() {
        List<Student> students = studentRepository.findAll();

        // Students by batch
        Map<String, Long> studentsByBatch = students.stream()
                .collect(Collectors.groupingBy(
                    Student::getBatch,
                    Collectors.counting()
                ));

        // Students by semester
        Map<Integer, Long> studentsBySemester = students.stream()
                .collect(Collectors.groupingBy(
                    Student::getSemester,
                    Collectors.counting()
                ));

        // Average CGPA
        double averageCgpa = students.stream()
                .mapToDouble(Student::getCgpa)
                .average()
                .orElse(0.0);

        // Students per mentor
        Map<String, Long> studentsPerMentor = students.stream()
                .filter(s -> s.getAssignedMentor() != null)
                .collect(Collectors.groupingBy(
                    s -> s.getAssignedMentor().getMentorId(),
                    Collectors.counting()
                ));

        // Attendance by batch
        Map<String, Double> attendanceByBatch = students.stream()
                .collect(Collectors.groupingBy(
                    Student::getBatch,
                    Collectors.averagingDouble(Student::getAttendance)
                ));

        return AnalyticsResponse.StudentStats.builder()
                .studentsByBatch(studentsByBatch)
                .studentsBySemester(studentsBySemester)
                .averageCgpa(averageCgpa)
                .studentsPerMentor(studentsPerMentor)
                .attendanceByBatch(attendanceByBatch)
                .build();
    }

    @Override
    public AnalyticsResponse.MentorStats getMentorStats() {
        List<Mentor> mentors = mentorRepository.findAll();

        // Mentors by years of experience
        Map<String, Long> mentorsByExperience = mentors.stream()
                .collect(Collectors.groupingBy(
                    mentor -> experienceRange(mentor.getYearsOfExperience()),
                    Collectors.counting()
                ));

        // Mentors by specialization
        Map<String, Long> mentorsBySpecialization = mentors.stream()
                .collect(Collectors.groupingBy(
                    Mentor::getSpecialization,
                    Collectors.counting()
                ));

        // Average students per mentor
        double averageStudentsPerMentor = mentors.stream()
                .mapToInt(m -> m.getAssignedStudents().size())
                .average()
                .orElse(0.0);

        // Top performing mentors
        List<AnalyticsResponse.MentorPerformance> topPerformingMentors = mentors.stream()
                .map(this::calculateMentorPerformance)
                .sorted(Comparator.comparingDouble(AnalyticsResponse.MentorPerformance::getAverageStudentCgpa).reversed())
                .limit(5)
                .collect(Collectors.toList());

        return AnalyticsResponse.MentorStats.builder()
                .mentorsByExperience(mentorsByExperience)
                .mentorsBySpecialization(mentorsBySpecialization)
                .averageStudentsPerMentor(averageStudentsPerMentor)
                .topPerformingMentors(topPerformingMentors)
                .build();
    }

    @Override
    public AnalyticsResponse getAllAnalytics() {
        return AnalyticsResponse.builder()
                .userCounts(getUserCountStats())
                .departmentStats(getDepartmentStats())
                .studentStats(getStudentStats())
                .mentorStats(getMentorStats())
                .build();
    }

    private String experienceRange(int years) {
        if (years < 2) return "0-2 years";
        if (years < 5) return "2-5 years";
        if (years < 10) return "5-10 years";
        return "10+ years";
    }

    private AnalyticsResponse.MentorPerformance calculateMentorPerformance(Mentor mentor) {
        List<Student> assignedStudents = mentor.getAssignedStudents();
        double avgCgpa = assignedStudents.stream()
                .mapToDouble(Student::getCgpa)
                .average()
                .orElse(0.0);
        
        double avgAttendance = assignedStudents.stream()
                .mapToDouble(Student::getAttendance)
                .average()
                .orElse(0.0);

        return AnalyticsResponse.MentorPerformance.builder()
                .mentorId(mentor.getMentorId())
                .mentorName(mentor.getFirstName() + " " + mentor.getLastName())
                .studentCount(assignedStudents.size())
                .averageStudentCgpa(avgCgpa)
                .averageStudentAttendance(avgAttendance)
                .build();
    }
} 