package com.mahesh.mentee_connect.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.mahesh.mentee_connect.dto.MentorResponse;
import com.mahesh.mentee_connect.dto.MeetingChangeRequest;
import com.mahesh.mentee_connect.dto.ProgressReport;
import com.mahesh.mentee_connect.model.Meeting;
import com.mahesh.mentee_connect.model.Student;
import com.mahesh.mentee_connect.service.MeetingService;
import com.mahesh.mentee_connect.service.StudentService;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/students")
@Tag(name = "Students", description = "Student management APIs")
public class StudentController {

    @Autowired
    private StudentService studentService;
    
    @Autowired
    private MeetingService meetingService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create student", description = "Create a new student (Admin only)")
    public ResponseEntity<Student> createStudent(@Valid @RequestBody Student student) {
        return ResponseEntity.ok(studentService.createStudent(student));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isCurrentUser(#id)")
    @Operation(summary = "Update student", description = "Update an existing student (Admin or self)")
    public ResponseEntity<Student> updateStudent(@PathVariable String id, @Valid @RequestBody Student student) {
        return ResponseEntity.ok(studentService.updateStudent(id, student));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete student", description = "Delete a student (Admin only)")
    public ResponseEntity<?> deleteStudent(@PathVariable String id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MENTOR') or @securityService.isCurrentUser(#id)")
    @Operation(summary = "Get student", description = "Get student by ID (Admin, Mentor, or self)")
    public ResponseEntity<Student> getStudent(@PathVariable String id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MENTOR')")
    @Operation(summary = "Get all students", description = "Get all students (Admin or Mentor)")
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/mentor/{mentorId}")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isCurrentMentor(#mentorId)")
    @Operation(summary = "Get mentor's students", description = "Get all students assigned to a mentor (Admin or mentor)")
    public ResponseEntity<List<Student>> getStudentsByMentor(@PathVariable String mentorId) {
        return ResponseEntity.ok(studentService.getStudentsByMentorId(mentorId));
    }

    @GetMapping("/{id}/meetings")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MENTOR') or @securityService.isCurrentUser(#id)")
    @Operation(summary = "Get student meetings", description = "Get all meetings for a student (Admin, Mentor, or self)")
    public ResponseEntity<List<Meeting>> getStudentMeetings(@PathVariable String id) {
        return ResponseEntity.ok(studentService.getStudentMeetings(id));
    }

    @PutMapping("/{studentId}/mentor/{mentorId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Assign mentor", description = "Assign a mentor to a student (Admin only)")
    public ResponseEntity<Student> assignMentorToStudent(
            @PathVariable String studentId,
            @PathVariable String mentorId) {
        return ResponseEntity.ok(studentService.assignMentorToStudent(studentId, mentorId));
    }

    @PutMapping("/{id}/progress")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MENTOR')")
    @Operation(summary = "Update progress", description = "Update student's progress (Admin or Mentor)")
    public ResponseEntity<Student> updateStudentProgress(
            @PathVariable String id,
            @RequestParam double attendance,
            @RequestParam double cgpa) {
        return ResponseEntity.ok(studentService.updateStudentProgress(id, attendance, cgpa));
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('STUDENT')")
    @Operation(summary = "Get current student", description = "Get current logged-in student's details")
    public ResponseEntity<Student> getCurrentStudent() {
        return ResponseEntity.ok(studentService.getCurrentStudent());
    }

    @GetMapping("/my-mentor")
    public ResponseEntity<MentorResponse> getMyMentor(Authentication authentication) {
        String studentId = ((Student) studentService.getCurrentStudent(authentication)).getId();
        return ResponseEntity.ok(studentService.getMyMentor(studentId));
    }

    @GetMapping("/meetings/upcoming")
    public ResponseEntity<List<Meeting>> getUpcomingMeetings(Authentication authentication) {
        String studentId = ((Student) studentService.getCurrentStudent(authentication)).getId();
        return ResponseEntity.ok(meetingService.getUpcomingMeetingsByStudent(studentId));
    }

    @GetMapping("/meetings/{meetingId}")
    public ResponseEntity<Meeting> getMeetingDetails(
            @PathVariable String meetingId,
            Authentication authentication) {
        String studentId = ((Student) studentService.getCurrentStudent(authentication)).getId();
        return ResponseEntity.ok(meetingService.getMeetingDetailsForStudent(meetingId, studentId));
    }

    @PostMapping("/meetings/{meetingId}/request-changes")
    public ResponseEntity<MeetingChangeRequest> requestMeetingChanges(
            @PathVariable String meetingId,
            @RequestBody MeetingChangeRequest request,
            Authentication authentication) {
        String studentId = ((Student) studentService.getCurrentStudent(authentication)).getId();
        return ResponseEntity.ok(
                meetingService.requestMeetingChanges(meetingId, studentId, request));
    }

    @GetMapping("/progress-reports")
    public ResponseEntity<List<ProgressReport>> getProgressReports(Authentication authentication) {
        String studentId = ((Student) studentService.getCurrentStudent(authentication)).getId();
        return ResponseEntity.ok(studentService.getProgressReports(studentId));
    }
}