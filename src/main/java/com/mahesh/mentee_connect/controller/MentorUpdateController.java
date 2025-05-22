package com.mahesh.mentee_connect.controller;

import com.mahesh.mentee_connect.model.MentorUpdate;
import com.mahesh.mentee_connect.service.MentorUpdateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mentors")
@Tag(name = "Mentor Updates", description = "Mentor update management APIs")
public class MentorUpdateController {

    @Autowired
    private MentorUpdateService mentorUpdateService;
    
    @PostMapping("/{mentorId}/updates")
    @PreAuthorize("hasRole('MENTOR') and @securityService.isCurrentUser(#mentorId)")
    @Operation(summary = "Create update", description = "Create a new update/announcement for mentees")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Update created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    public ResponseEntity<MentorUpdate> createUpdate(
            @PathVariable String mentorId, @Valid @RequestBody MentorUpdate update) {
        // Set the mentor ID from the path parameter
        update.setMentorId(mentorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(mentorUpdateService.createUpdate(update));
    }
    
    @GetMapping("/{mentorId}/updates")
    @PreAuthorize("hasRole('MENTOR') and @securityService.isCurrentUser(#mentorId)")
    @Operation(summary = "Get updates", description = "Get all updates/announcements created by a mentor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Updates retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    public ResponseEntity<List<MentorUpdate>> getUpdatesByMentor(@PathVariable String mentorId) {
        return ResponseEntity.ok(mentorUpdateService.getUpdatesByMentor(mentorId));
    }
    
    @GetMapping("/{mentorId}/students/{studentId}/updates")
    @PreAuthorize("hasRole('MENTOR') and @securityService.isCurrentUser(#mentorId)")
    @Operation(summary = "Get student updates", description = "Get all updates/announcements for a specific mentee")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Updates retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden"),
        @ApiResponse(responseCode = "404", description = "Student not found")
    })
    public ResponseEntity<List<MentorUpdate>> getUpdatesByMentorAndStudent(
            @PathVariable String mentorId, @PathVariable String studentId) {
        return ResponseEntity.ok(mentorUpdateService.getUpdatesByMentorAndStudent(mentorId, studentId));
    }
    
    @PutMapping("/updates/{updateId}")
    @PreAuthorize("hasRole('MENTOR') and @securityService.isUpdateOwner(#updateId)")
    @Operation(summary = "Update announcement", description = "Update an existing announcement")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Update updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden"),
        @ApiResponse(responseCode = "404", description = "Update not found")
    })
    public ResponseEntity<MentorUpdate> updateUpdate(
            @PathVariable String updateId, @Valid @RequestBody MentorUpdate update) {
        return ResponseEntity.ok(mentorUpdateService.updateUpdate(updateId, update));
    }
    
    @DeleteMapping("/updates/{updateId}")
    @PreAuthorize("hasRole('MENTOR') and @securityService.isUpdateOwner(#updateId)")
    @Operation(summary = "Delete update", description = "Delete an existing update/announcement")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Update deleted successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden"),
        @ApiResponse(responseCode = "404", description = "Update not found")
    })
    public ResponseEntity<Void> deleteUpdate(@PathVariable String updateId) {
        mentorUpdateService.deleteUpdate(updateId);
        return ResponseEntity.noContent().build();
    }
} 