package com.mahesh.mentee_connect.controller;

import com.mahesh.mentee_connect.model.MenteeAttendance;
import com.mahesh.mentee_connect.service.MenteeAttendanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mentors")
@Tag(name = "Mentee Attendance", description = "Mentee attendance management APIs")
public class MenteeAttendanceController {

    @Autowired
    private MenteeAttendanceService attendanceService;
    
    @PostMapping("/{mentorId}/attendance")
    @PreAuthorize("hasRole('MENTOR') and @securityService.isCurrentUser(#mentorId)")
    @Operation(summary = "Record attendance", description = "Record attendance for a mentoring session")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Attendance recorded successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    public ResponseEntity<MenteeAttendance> recordAttendance(
            @PathVariable String mentorId, @Valid @RequestBody MenteeAttendance attendance) {
        // Set the mentor ID from the path parameter
        attendance.setMentorId(mentorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(attendanceService.recordAttendance(attendance));
    }
    
    @GetMapping("/{mentorId}/attendance")
    @PreAuthorize("hasRole('MENTOR') and @securityService.isCurrentUser(#mentorId)")
    @Operation(summary = "Get attendance records", description = "Get all attendance records by a mentor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Attendance records retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    public ResponseEntity<List<MenteeAttendance>> getAttendanceByMentor(@PathVariable String mentorId) {
        return ResponseEntity.ok(attendanceService.getAttendanceByMentor(mentorId));
    }
    
    @GetMapping("/{mentorId}/students/{studentId}/attendance")
    @PreAuthorize("hasRole('MENTOR') and @securityService.isCurrentUser(#mentorId)")
    @Operation(summary = "Get student attendance", description = "Get attendance records for a specific student")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Attendance records retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden"),
        @ApiResponse(responseCode = "404", description = "Student not found")
    })
    public ResponseEntity<List<MenteeAttendance>> getAttendanceByMentorAndStudent(
            @PathVariable String mentorId, @PathVariable String studentId) {
        return ResponseEntity.ok(attendanceService.getAttendanceByMentorAndStudent(mentorId, studentId));
    }
    
    @GetMapping("/{mentorId}/attendance/{startDate}/{endDate}")
    @PreAuthorize("hasRole('MENTOR') and @securityService.isCurrentUser(#mentorId)")
    @Operation(summary = "Get attendance by date range", description = "Get attendance records within a date range")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Attendance records retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    public ResponseEntity<List<MenteeAttendance>> getAttendanceByDateRange(
            @PathVariable String mentorId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(attendanceService.getAttendanceByMentorAndDateRange(mentorId, startDate, endDate));
    }
    
    @GetMapping("/{mentorId}/students/{studentId}/attendance/percentage/{startDate}/{endDate}")
    @PreAuthorize("hasRole('MENTOR') and @securityService.isCurrentUser(#mentorId)")
    @Operation(summary = "Get attendance percentage", description = "Calculate attendance percentage for a student")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Attendance percentage calculated successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden"),
        @ApiResponse(responseCode = "404", description = "Student not found")
    })
    public ResponseEntity<Map<String, Double>> calculateAttendancePercentage(
            @PathVariable String mentorId,
            @PathVariable String studentId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(attendanceService.calculateAttendancePercentage(mentorId, studentId, startDate, endDate));
    }
    
    @PutMapping("/attendance/{attendanceId}")
    @PreAuthorize("hasRole('MENTOR') and @securityService.isAttendanceOwner(#attendanceId)")
    @Operation(summary = "Update attendance", description = "Update an existing attendance record")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Attendance updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden"),
        @ApiResponse(responseCode = "404", description = "Attendance record not found")
    })
    public ResponseEntity<MenteeAttendance> updateAttendance(
            @PathVariable String attendanceId, @Valid @RequestBody MenteeAttendance attendance) {
        return ResponseEntity.ok(attendanceService.updateAttendance(attendanceId, attendance));
    }
    
    @DeleteMapping("/attendance/{attendanceId}")
    @PreAuthorize("hasRole('MENTOR') and @securityService.isAttendanceOwner(#attendanceId)")
    @Operation(summary = "Delete attendance", description = "Delete an existing attendance record")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Attendance deleted successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden"),
        @ApiResponse(responseCode = "404", description = "Attendance record not found")
    })
    public ResponseEntity<Void> deleteAttendance(@PathVariable String attendanceId) {
        attendanceService.deleteAttendance(attendanceId);
        return ResponseEntity.noContent().build();
    }
} 