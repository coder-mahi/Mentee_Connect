package com.mentee.connect.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mentee.connect.entity.VideoCall;
import com.mentee.connect.repository.VideoCallRepository;

@Service
public class VideoCallService {

    @Autowired
    private VideoCallRepository videoCallRepository;

    public VideoCall initializeCall(String meetingId, String mentorId) {
        VideoCall call = new VideoCall();
        call.setMeetingId(meetingId);
        call.setStatus("INITIALIZED");
        call.setStartTime(LocalDateTime.now());
        return videoCallRepository.save(call);
    }

    public VideoCall joinCall(String callId, String participantId, String role) {
        VideoCall call = videoCallRepository.findById(callId)
                .orElseThrow(() -> new RuntimeException("Video call not found"));

        VideoCall.Participant participant = new VideoCall.Participant();
        participant.setUserId(participantId);
        participant.setRole(role);
        participant.setJoinedAt(LocalDateTime.now());

        call.getParticipants().add(participant);
        call.setStatus("JOINED");
        return videoCallRepository.save(call);
    }

    public VideoCall leaveCall(String callId, String participantId) {
        VideoCall call = videoCallRepository.findById(callId)
                .orElseThrow(() -> new RuntimeException("Video call not found"));

        call.getParticipants().stream()
                .filter(p -> p.getUserId().equals(participantId))
                .findFirst()
                .ifPresent(p -> p.setLeftAt(LocalDateTime.now()));

        call.setStatus("LEFT");
        return videoCallRepository.save(call);
    }

    public VideoCall endCall(String callId, String mentorId) {
        VideoCall call = videoCallRepository.findById(callId)
                .orElseThrow(() -> new RuntimeException("Video call not found"));

        call.setStatus("ENDED");
        call.setEndTime(LocalDateTime.now());
        return videoCallRepository.save(call);
    }

    public List<VideoCall> getActiveCalls() {
        return videoCallRepository.findByStatus("JOINED");
    }
} 