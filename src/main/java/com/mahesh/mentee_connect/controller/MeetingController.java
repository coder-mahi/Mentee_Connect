package com.mahesh.mentee_connect.controller;

import com.mahesh.mentee_connect.model.Meeting;
import com.mahesh.mentee_connect.service.MeetingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/meetings")
@Tag(name = "Meetings", description = "Meeting management APIs")
public class MeetingController {
    @Autowired
    private MeetingService meetingService;

    @PostMapping
    @PreAuthorize("hasRole('MENTOR')")
    @Operation(summary = "Create meeting", description = "Create a new meeting (Mentor only)")
    public ResponseEntity<Meeting> createMeeting(@Valid @RequestBody Meeting meeting) {
        return ResponseEntity.ok(meetingService.createMeeting(meeting));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MENTOR')")
    @Operation(summary = "Update meeting", description = "Update an existing meeting (Mentor only)")
    public ResponseEntity<Meeting> updateMeeting(@PathVariable String id, @Valid @RequestBody Meeting meeting) {
        return ResponseEntity.ok(meetingService.updateMeeting(id, meeting));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MENTOR')")
    @Operation(summary = "Delete meeting", description = "Delete a meeting (Mentor only)")
    public ResponseEntity<?> deleteMeeting(@PathVariable String id) {
        meetingService.deleteMeeting(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MENTOR') or hasRole('STUDENT')")
    @Operation(summary = "Get meeting", description = "Get meeting by ID")
    public ResponseEntity<Meeting> getMeeting(@PathVariable String id) {
        return ResponseEntity.ok(meetingService.getMeetingById(id));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all meetings", description = "Get all meetings (Admin only)")
    public ResponseEntity<List<Meeting>> getAllMeetings() {
        return ResponseEntity.ok(meetingService.getAllMeetings());
    }

    @GetMapping("/mentor/{mentorId}")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isCurrentUser(#mentorId)")
    @Operation(summary = "Get mentor meetings", description = "Get all meetings for a mentor")
    public ResponseEntity<List<Meeting>> getMeetingsByMentor(@PathVariable String mentorId) {
        return ResponseEntity.ok(meetingService.getMeetingsByMentor(mentorId));
    }

    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MENTOR') or @securityService.isCurrentUser(#studentId)")
    @Operation(summary = "Get student meetings", description = "Get all meetings for a student")
    public ResponseEntity<List<Meeting>> getMeetingsByStudent(@PathVariable String studentId) {
        return ResponseEntity.ok(meetingService.getMeetingsByStudent(studentId));
    }

    @GetMapping("/range")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MENTOR') or hasRole('STUDENT')")
    @Operation(summary = "Get meetings by time range", description = "Get meetings within a time range")
    public ResponseEntity<List<Meeting>> getMeetingsByTimeRange(
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end) {
        return ResponseEntity.ok(meetingService.getMeetingsByTimeRange(start, end));
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('MENTOR')")
    @Operation(summary = "Update meeting status", description = "Update the status of a meeting (Mentor only)")
    public ResponseEntity<Meeting> updateMeetingStatus(
            @PathVariable String id,
            @RequestParam Meeting.MeetingStatus status) {
        return ResponseEntity.ok(meetingService.updateMeetingStatus(id, status));
    }

    @PutMapping("/{id}/notes")
    @PreAuthorize("hasRole('MENTOR')")
    @Operation(summary = "Add meeting notes", description = "Add notes to a meeting (Mentor only)")
    public ResponseEntity<Meeting> addMeetingNotes(
            @PathVariable String id,
            @RequestParam String notes) {
        return ResponseEntity.ok(meetingService.addMeetingNotes(id, notes));
    }
}