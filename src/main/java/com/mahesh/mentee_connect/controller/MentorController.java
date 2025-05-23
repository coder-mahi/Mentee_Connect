package com.mahesh.mentee_connect.controller;

import com.mahesh.mentee_connect.model.Mentor;
import com.mahesh.mentee_connect.model.Student;
import com.mahesh.mentee_connect.model.Meeting;
import com.mahesh.mentee_connect.service.MentorService;
import com.mahesh.mentee_connect.dto.MenteeUpdateRequest;
import com.mahesh.mentee_connect.dto.StudentMentorDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/mentors")
@Tag(name = "Mentors", description = "Mentor management APIs")
public class MentorController {
    @Autowired
    private MentorService mentorService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create mentor", description = "Create a new mentor (Admin only)")
    public ResponseEntity<Mentor> createMentor(@Valid @RequestBody Mentor mentor) {
        return ResponseEntity.ok(mentorService.createMentor(mentor));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isCurrentUser(#id)")
    @Operation(summary = "Update mentor", description = "Update an existing mentor (Admin or self)")
    public ResponseEntity<Mentor> updateMentor(@PathVariable String id, @Valid @RequestBody Mentor mentor) {
        return ResponseEntity.ok(mentorService.updateMentor(id, mentor));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete mentor", description = "Delete a mentor (Admin only)")
    public ResponseEntity<?> deleteMentor(@PathVariable String id) {
        mentorService.deleteMentor(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isCurrentUser(#id) or hasRole('STUDENT')")
    @Operation(summary = "Get mentor", description = "Get mentor by ID (Admin, self, or student)")
    public ResponseEntity<Mentor> getMentor(@PathVariable String id) {
        return ResponseEntity.ok(mentorService.getMentorById(id));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all mentors", description = "Get all mentors (Admin only)")
    public ResponseEntity<List<Mentor>> getAllMentors() {
        return ResponseEntity.ok(mentorService.getAllMentors());
    }

    @GetMapping("/{id}/students")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isCurrentUser(#id)")
    @Operation(summary = "Get mentor's students", description = "Get all students assigned to a mentor (Admin or self)")
    public ResponseEntity<List<StudentMentorDTO>> getMentorStudents(@PathVariable String id) {
        return ResponseEntity.ok(mentorService.getMentorStudents(id));
    }

    @GetMapping("/{id}/meetings")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isCurrentUser(#id)")
    @Operation(summary = "Get mentor meetings", description = "Get all meetings for a mentor (Admin or self)")
    public ResponseEntity<List<Meeting>> getMentorMeetings(@PathVariable String id) {
        return ResponseEntity.ok(mentorService.getMentorMeetings(id));
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('MENTOR')")
    @Operation(summary = "Get current mentor", description = "Get current logged-in mentor's details")
    public ResponseEntity<Mentor> getCurrentMentor() {
        return ResponseEntity.ok(mentorService.getCurrentMentor());
    }

    @GetMapping("/{id}/slots")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STUDENT')")
    @Operation(summary = "Check mentor slots", description = "Check if mentor has available slots for new students")
    public ResponseEntity<Boolean> hasAvailableSlots(@PathVariable String id) {
        return ResponseEntity.ok(mentorService.hasAvailableSlots(id));
    }

    @PutMapping("/{mentorId}/students/{studentId}")
    @PreAuthorize("hasRole('MENTOR') and @securityService.isCurrentUser(#mentorId)")
    @Operation(summary = "Update mentee information", description = "Update information for a mentee assigned to this mentor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Student information updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden"),
        @ApiResponse(responseCode = "404", description = "Mentor or student not found")
    })
    public ResponseEntity<Student> updateStudentAsMentor(
            @PathVariable String mentorId,
            @PathVariable String studentId,
            @Valid @RequestBody MenteeUpdateRequest updateRequest) {
        return ResponseEntity.ok(mentorService.updateStudentAsMentor(mentorId, studentId, updateRequest));
    }

    @GetMapping("/{mentorId}/students/{studentId}")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isCurrentUser(#mentorId)")
    @Operation(summary = "Get specific mentee details", description = "Get details of a specific mentee assigned to the mentor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved mentee details"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden"),
        @ApiResponse(responseCode = "404", description = "Mentor or student not found")
    })
    public ResponseEntity<StudentMentorDTO> getMenteeDetails(
            @PathVariable String mentorId,
            @PathVariable String studentId) {
        return ResponseEntity.ok(mentorService.getMenteeDetails(mentorId, studentId));
    }

    @PutMapping("/{mentorId}/students/{studentId}/update")
    @PreAuthorize("hasRole('MENTOR') and @securityService.isCurrentUser(#mentorId)")
    @Operation(summary = "Update mentee details", description = "Update details of a mentee assigned to this mentor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Student information updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden"),
        @ApiResponse(responseCode = "404", description = "Mentor or student not found")
    })
    public ResponseEntity<StudentMentorDTO> updateMenteeDetails(
            @PathVariable String mentorId,
            @PathVariable String studentId,
            @Valid @RequestBody MenteeUpdateRequest updateRequest) {
        return ResponseEntity.ok(mentorService.updateMenteeDetails(mentorId, studentId, updateRequest));
    }
}