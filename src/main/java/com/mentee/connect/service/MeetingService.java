package com.mentee.connect.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mentee.connect.entity.Meeting;
import com.mentee.connect.repository.MeetingRepository;

@Service
public class MeetingService {

    @Autowired
    private MeetingRepository meetingRepository;

    public Meeting createMeeting(Meeting meeting) {
        return meetingRepository.save(meeting);
    }

    public List<Meeting> getMentorMeetings(String mentorId) {
        return meetingRepository.findByMentorId(mentorId);
    }

    public List<Meeting> getMenteeMeetings(String menteeId) {
        return meetingRepository.findByMenteesContaining(menteeId);
    }

    public Meeting getMeetingDetails(String meetingId) {
        return meetingRepository.findById(meetingId)
                .orElseThrow(() -> new RuntimeException("Meeting not found"));
    }

    public Meeting updateMeetingStatus(String meetingId, String status) {
        Meeting meeting = getMeetingDetails(meetingId);
        meeting.setStatus(status);
        if ("COMPLETED".equals(status)) {
            meeting.setCompletedAt(LocalDateTime.now());
        }
        return meetingRepository.save(meeting);
    }

    public boolean deleteMeeting(String meetingId) {
        try {
            meetingRepository.deleteById(meetingId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
} 