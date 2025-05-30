// 4. Video Call Controller (src/main/java/com/mahesh/mentee_connect/controller/VideoCallController.java)
package com.mahesh.mentee_connect.controller;

import com.mahesh.mentee_connect.entity.VideoCall;
import com.mahesh.mentee_connect.service.VideoCallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/video-calls")
@CrossOrigin(origins = "*") // Configure as per your frontend URL
public class VideoCallController {

    @Autowired
    private VideoCallService videoCallService;

    // Create new video call (Mentor only)
    @PostMapping("/create")
    public ResponseEntity<?> createVideoCall(@RequestBody Map<String, Object> request) {
        try {
            String mentorId = (String) request.get("mentorId");
            String mentorName = (String) request.get("mentorName");
            String title = (String) request.get("title");
            String description = (String) request.get("description");
            String scheduledTimeStr = (String) request.get("scheduledTime");
            Integer maxParticipants = (Integer) request.get("maxParticipants");
            String password = (String) request.get("password");
            
            LocalDateTime scheduledTime = LocalDateTime.parse(scheduledTimeStr);
            
            VideoCall videoCall = videoCallService.createVideoCall(
                mentorId, mentorName, title, description, scheduledTime, maxParticipants, password
            );
            
            return ResponseEntity.ok(videoCall);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating video call: " + e.getMessage());
        }
    }

    // Get video calls by mentor
    @GetMapping("/mentor/{mentorId}")
    public ResponseEntity<List<VideoCall>> getVideoCallsByMentor(@PathVariable String mentorId) {
        List<VideoCall> videoCalls = videoCallService.getVideoCallsByMentor(mentorId);
        return ResponseEntity.ok(videoCalls);
    }

    // Get available video calls for mentees
    @GetMapping("/available")
    public ResponseEntity<List<VideoCall>> getAvailableVideoCalls() {
        List<VideoCall> videoCalls = videoCallService.getAvailableVideoCallsForMentees();
        return ResponseEntity.ok(videoCalls);
    }

    // Join video call (Mentee)
    @PostMapping("/join")
    public ResponseEntity<?> joinVideoCall(@RequestBody Map<String, String> request) {
        try {
            String roomId = request.get("roomId");
            String menteeId = request.get("menteeId");
            String password = request.get("password");
            
            VideoCall videoCall = videoCallService.joinVideoCall(roomId, menteeId, password);
            return ResponseEntity.ok(videoCall);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error joining video call: " + e.getMessage());
        }
    }

    // Start video call (Mentor only)
    @PostMapping("/start")
    public ResponseEntity<?> startVideoCall(@RequestBody Map<String, String> request) {
        try {
            String roomId = request.get("roomId");
            String mentorId = request.get("mentorId");
            
            VideoCall videoCall = videoCallService.startVideoCall(roomId, mentorId);
            return ResponseEntity.ok(videoCall);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error starting video call: " + e.getMessage());
        }
    }

    // End video call (Mentor only)
    @PostMapping("/end")
    public ResponseEntity<?> endVideoCall(@RequestBody Map<String, String> request) {
        try {
            String roomId = request.get("roomId");
            String mentorId = request.get("mentorId");
            
            VideoCall videoCall = videoCallService.endVideoCall(roomId, mentorId);
            return ResponseEntity.ok(videoCall);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error ending video call: " + e.getMessage());
        }
    }

    // Leave video call (Mentee)
    @PostMapping("/leave")
    public ResponseEntity<?> leaveVideoCall(@RequestBody Map<String, String> request) {
        try {
            String roomId = request.get("roomId");
            String menteeId = request.get("menteeId");
            
            VideoCall videoCall = videoCallService.leaveVideoCall(roomId, menteeId);
            return ResponseEntity.ok(videoCall);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error leaving video call: " + e.getMessage());
        }
    }

    // Get video call details by room ID
    @GetMapping("/room/{roomId}")
    public ResponseEntity<?> getVideoCallByRoomId(@PathVariable String roomId) {
        try {
            VideoCall videoCall = videoCallService.getVideoCallByRoomId(roomId);
            return ResponseEntity.ok(videoCall);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching video call: " + e.getMessage());
        }
    }

    // Get video calls for mentee
    @GetMapping("/mentee/{menteeId}")
    public ResponseEntity<List<VideoCall>> getVideoCallsForMentee(@PathVariable String menteeId) {
        List<VideoCall> videoCalls = videoCallService.getVideoCallsForMentee(menteeId);
        return ResponseEntity.ok(videoCalls);
    }
}