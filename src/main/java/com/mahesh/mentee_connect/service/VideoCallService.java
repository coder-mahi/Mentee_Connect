// 3. Video Call Service (src/main/java/com/mahesh/mentee_connect/service/VideoCallService.java)
package com.mahesh.mentee_connect.service;

import com.mahesh.mentee_connect.entity.VideoCall;
import com.mahesh.mentee_connect.repository.VideoCallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VideoCallService {
    
    @Autowired
    private VideoCallRepository videoCallRepository;

    // Create a new video call (only mentors can create)
    public VideoCall createVideoCall(String mentorId, String mentorName, String title, 
                                   String description, LocalDateTime scheduledTime, 
                                   Integer maxParticipants, String password) {
        VideoCall videoCall = new VideoCall(mentorId, mentorName, title, description, scheduledTime);
        videoCall.setMaxParticipants(maxParticipants);
        videoCall.setMeetingPassword(password);
        return videoCallRepository.save(videoCall);
    }

    // Get all video calls by mentor
    public List<VideoCall> getVideoCallsByMentor(String mentorId) {
        return videoCallRepository.findByMentorId(mentorId);
    }

    // Get all available video calls (for mentees to join)
    public List<VideoCall> getAvailableVideoCallsForMentees() {
        return videoCallRepository.findByStatus("SCHEDULED");
    }

    // Join a video call (for mentees)
    public VideoCall joinVideoCall(String roomId, String menteeId, String password) {
        Optional<VideoCall> optionalCall = videoCallRepository.findByRoomId(roomId);
        
        if (optionalCall.isEmpty()) {
            throw new RuntimeException("Video call not found");
        }
        
        VideoCall videoCall = optionalCall.get();
        
        // Check password if required
        if (videoCall.getMeetingPassword() != null && !videoCall.getMeetingPassword().equals(password)) {
            throw new RuntimeException("Invalid meeting password");
        }
        
        // Check if already joined
        if (videoCall.getParticipants().contains(menteeId)) {
            return videoCall; // Already joined
        }
        
        // Check max participants limit
        if (videoCall.getMaxParticipants() != null && 
            videoCall.getParticipants().size() >= videoCall.getMaxParticipants()) {
            throw new RuntimeException("Meeting is full");
        }
        
        // Add participant
        videoCall.getParticipants().add(menteeId);
        return videoCallRepository.save(videoCall);
    }

    // Start video call (only mentor can start)
    public VideoCall startVideoCall(String roomId, String mentorId) {
        Optional<VideoCall> optionalCall = videoCallRepository.findByRoomId(roomId);
        
        if (optionalCall.isEmpty()) {
            throw new RuntimeException("Video call not found");
        }
        
        VideoCall videoCall = optionalCall.get();
        
        if (!videoCall.getMentorId().equals(mentorId)) {
            throw new RuntimeException("Only the mentor can start the call");
        }
        
        videoCall.setStatus("LIVE");
        videoCall.setStartedAt(LocalDateTime.now());
        return videoCallRepository.save(videoCall);
    }

    // End video call (only mentor can end)
    public VideoCall endVideoCall(String roomId, String mentorId) {
        Optional<VideoCall> optionalCall = videoCallRepository.findByRoomId(roomId);
        
        if (optionalCall.isEmpty()) {
            throw new RuntimeException("Video call not found");
        }
        
        VideoCall videoCall = optionalCall.get();
        
        if (!videoCall.getMentorId().equals(mentorId)) {
            throw new RuntimeException("Only the mentor can end the call");
        }
        
        videoCall.setStatus("ENDED");
        videoCall.setEndedAt(LocalDateTime.now());
        return videoCallRepository.save(videoCall);
    }

    // Leave video call (for mentees)
    public VideoCall leaveVideoCall(String roomId, String menteeId) {
        Optional<VideoCall> optionalCall = videoCallRepository.findByRoomId(roomId);
        
        if (optionalCall.isEmpty()) {
            throw new RuntimeException("Video call not found");
        }
        
        VideoCall videoCall = optionalCall.get();
        videoCall.getParticipants().remove(menteeId);
        return videoCallRepository.save(videoCall);
    }

    // Get video call by room ID
    public VideoCall getVideoCallByRoomId(String roomId) {
        return videoCallRepository.findByRoomId(roomId)
                .orElseThrow(() -> new RuntimeException("Video call not found"));
    }

    // Get video calls that mentee has joined
    public List<VideoCall> getVideoCallsForMentee(String menteeId) {
        return videoCallRepository.findByParticipantsContaining(menteeId);
    }
}
