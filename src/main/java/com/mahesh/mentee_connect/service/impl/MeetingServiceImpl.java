package com.mahesh.mentee_connect.service.impl;

import com.mahesh.mentee_connect.model.Meeting;
import com.mahesh.mentee_connect.model.Mentor;
import com.mahesh.mentee_connect.model.Student;
import com.mahesh.mentee_connect.repository.MeetingRepository;
import com.mahesh.mentee_connect.repository.MentorRepository;
import com.mahesh.mentee_connect.repository.StudentRepository;
import com.mahesh.mentee_connect.service.MeetingService;
import com.mahesh.mentee_connect.exception.ResourceNotFoundException;
import com.mahesh.mentee_connect.payload.request.MeetingRequest;
import com.mahesh.mentee_connect.dto.MeetingChangeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MeetingServiceImpl implements MeetingService {
    
    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private MentorRepository mentorRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Override
    @Transactional
    public Meeting createMeeting(Meeting meeting) {
        meeting.setCreatedAt(LocalDateTime.now());
        meeting.setUpdatedAt(LocalDateTime.now());
        return meetingRepository.save(meeting);
    }

    @Override
    @Transactional
    public Meeting updateMeeting(String id, Meeting meetingDetails) {
        Meeting meeting = getMeetingById(id);
        meeting.setTitle(meetingDetails.getTitle());
        meeting.setDescription(meetingDetails.getDescription());
        meeting.setScheduledTime(meetingDetails.getScheduledTime());
        meeting.setEndTime(meetingDetails.getEndTime());
        meeting.setStatus(meetingDetails.getStatus());
        meeting.setMeetingLink(meetingDetails.getMeetingLink());
        meeting.setLocation(meetingDetails.getLocation());
        meeting.setType(meetingDetails.getType());
        meeting.setNotes(meetingDetails.getNotes());
        meeting.setUpdatedAt(LocalDateTime.now());
        return meetingRepository.save(meeting);
    }

    @Override
    @Transactional
    public void deleteMeeting(String id) {
        Meeting meeting = getMeetingById(id);
        meetingRepository.delete(meeting);
    }

    @Override
    @Transactional(readOnly = true)
    public Meeting getMeetingById(String id) {
        return meetingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Meeting not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Meeting> getAllMeetings() {
        return meetingRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Meeting> getMeetingsByMentor(String mentorId) {
        return meetingRepository.findByMentorId(mentorId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Meeting> getMeetingsByStudent(String studentId) {
        return meetingRepository.findByStudentId(studentId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Meeting> getMeetingsByMentorAndStudent(String mentorId, String studentId) {
        return meetingRepository.findByMentorIdAndStudentId(mentorId, studentId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Meeting> getMeetingsByTimeRange(LocalDateTime start, LocalDateTime end) {
        return meetingRepository.findByScheduledTimeBetween(start, end);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Meeting> getMeetingsByMentorAndTimeRange(String mentorId, LocalDateTime start, LocalDateTime end) {
        return meetingRepository.findByMentorIdAndScheduledTimeBetween(mentorId, start, end);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Meeting> getMeetingsByStudentAndTimeRange(String studentId, LocalDateTime start, LocalDateTime end) {
        return meetingRepository.findByStudentIdAndScheduledTimeBetween(studentId, start, end);
    }

    @Override
    @Transactional
    public Meeting updateMeetingStatus(String id, Meeting.MeetingStatus status) {
        Meeting meeting = getMeetingById(id);
        meeting.setStatus(status);
        meeting.setUpdatedAt(LocalDateTime.now());
        return meetingRepository.save(meeting);
    }

    @Override
    @Transactional
    public Meeting addMeetingNotes(String id, String notes) {
        Meeting meeting = getMeetingById(id);
        meeting.setNotes(notes);
        meeting.setUpdatedAt(LocalDateTime.now());
        return meetingRepository.save(meeting);
    }

    @Override
    @Transactional
    public Meeting scheduleMeeting(MeetingRequest request) {
        Mentor mentor = mentorRepository.findById(request.getMentorId())
                .orElseThrow(() -> new ResourceNotFoundException("Mentor", "id", request.getMentorId()));
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", request.getStudentId()));

        Meeting meeting = new Meeting();
        meeting.setTitle(request.getTitle());
        meeting.setDescription(request.getDescription());
        meeting.setScheduledTime(request.getMeetingTime());
        meeting.setMentor(mentor);
        meeting.setStudent(student);
        meeting.setStatus(Meeting.MeetingStatus.SCHEDULED);
        meeting.setCreatedAt(LocalDateTime.now());
        meeting.setUpdatedAt(LocalDateTime.now());
        
        return meetingRepository.save(meeting);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Meeting> getUpcomingMeetingsByMentor(String mentorId) {
        return meetingRepository.findByMentorIdAndStatusAndScheduledTimeAfter(
            mentorId, Meeting.MeetingStatus.SCHEDULED, LocalDateTime.now());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Meeting> getUpcomingMeetingsByStudent(String studentId) {
        return meetingRepository.findByStudentIdAndStatusAndScheduledTimeAfter(
            studentId, Meeting.MeetingStatus.SCHEDULED, LocalDateTime.now());
    }

    @Override
    @Transactional(readOnly = true)
    public Meeting getMeetingDetailsForStudent(String meetingId, String studentId) {
        Meeting meeting = getMeetingById(meetingId);
        if (!meeting.getStudent().getId().equals(studentId)) {
            throw new ResourceNotFoundException("Meeting not found for student", "id", meetingId);
        }
        return meeting;
    }

    @Override
    @Transactional
    public MeetingChangeRequest requestMeetingChanges(String meetingId, String studentId, MeetingChangeRequest request) {
        Meeting meeting = getMeetingDetailsForStudent(meetingId, studentId);
        // Store the change request and return it
        // You may want to add additional validation/processing here
        return request;
    }
}