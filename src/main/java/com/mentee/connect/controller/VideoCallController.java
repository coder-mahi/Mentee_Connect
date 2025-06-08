package com.mentee.connect.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mentee.connect.entity.VideoCall;
import com.mentee.connect.service.VideoCallService;

@RestController
@RequestMapping("/api/video-calls")
public class VideoCallController {

    @Autowired
    private VideoCallService videoCallService;

    @PostMapping
    public ResponseEntity<VideoCall> initializeVideoCall(@RequestBody VideoCallRequest request) {
        return ResponseEntity.ok(videoCallService.initializeCall(request.getMeetingId(), request.getMentorId()));
    }

    @PostMapping("/{callId}/join")
    public ResponseEntity<VideoCall> joinVideoCall(
            @PathVariable String callId,
            @RequestBody JoinRequest request) {
        return ResponseEntity.ok(videoCallService.joinCall(callId, request.getParticipantId(), request.getRole()));
    }

    @PostMapping("/{callId}/leave")
    public ResponseEntity<VideoCall> leaveVideoCall(
            @PathVariable String callId,
            @RequestBody LeaveRequest request) {
        return ResponseEntity.ok(videoCallService.leaveCall(callId, request.getParticipantId()));
    }

    @PostMapping("/{callId}/end")
    public ResponseEntity<VideoCall> endVideoCall(
            @PathVariable String callId,
            @RequestBody EndRequest request) {
        return ResponseEntity.ok(videoCallService.endCall(callId, request.getMentorId()));
    }

    @GetMapping("/active")
    public ResponseEntity<List<VideoCall>> getActiveCalls() {
        return ResponseEntity.ok(videoCallService.getActiveCalls());
    }

    private static class VideoCallRequest {
        private String meetingId;
        private String mentorId;
        public String getMeetingId() { return meetingId; }
        public void setMeetingId(String meetingId) { this.meetingId = meetingId; }
        public String getMentorId() { return mentorId; }
        public void setMentorId(String mentorId) { this.mentorId = mentorId; }
    }

    private static class JoinRequest {
        private String participantId;
        private String role;
        public String getParticipantId() { return participantId; }
        public void setParticipantId(String participantId) { this.participantId = participantId; }
        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }
    }

    private static class LeaveRequest {
        private String participantId;
        public String getParticipantId() { return participantId; }
        public void setParticipantId(String participantId) { this.participantId = participantId; }
    }

    private static class EndRequest {
        private String mentorId;
        public String getMentorId() { return mentorId; }
        public void setMentorId(String mentorId) { this.mentorId = mentorId; }
    }
} 