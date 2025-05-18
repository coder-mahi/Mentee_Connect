package com.mahesh.mentee_connect.payload.response;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.Map;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnalyticsResponse {
    private UserCountStats userCounts;
    private DepartmentStats departmentStats;
    private StudentStats studentStats;
    private MentorStats mentorStats;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserCountStats {
        private long totalUsers;
        private long totalStudents;
        private long totalMentors;
        private long totalAdmins;
        private long activeUsers;
        private long inactiveUsers;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DepartmentStats {
        private Map<String, Long> studentsPerDepartment;
        private Map<String, Long> mentorsPerDepartment;
        private Map<String, Double> averageCgpaByDepartment;
        private List<String> topPerformingDepartments;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StudentStats {
        private Map<String, Long> studentsByBatch;
        private Map<Integer, Long> studentsBySemester;
        private double averageCgpa;
        private Map<String, Long> studentsPerMentor;
        private Map<String, Double> attendanceByBatch;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MentorStats {
        private Map<String, Long> mentorsByExperience;
        private Map<String, Long> mentorsBySpecialization;
        private double averageStudentsPerMentor;
        private List<MentorPerformance> topPerformingMentors;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MentorPerformance {
        private String mentorId;
        private String mentorName;
        private long studentCount;
        private double averageStudentCgpa;
        private double averageStudentAttendance;
    }
} 