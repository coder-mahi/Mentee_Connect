package com.mahesh.mentee_connect.service.impl;

import com.mahesh.mentee_connect.model.Mentor;
import com.mahesh.mentee_connect.model.Student;
import com.mahesh.mentee_connect.model.Meeting;
import com.mahesh.mentee_connect.repository.MentorRepository;
import com.mahesh.mentee_connect.repository.StudentRepository;
import com.mahesh.mentee_connect.repository.MeetingRepository;
import com.mahesh.mentee_connect.service.MentorService;
import com.mahesh.mentee_connect.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class MentorServiceImpl implements MentorService {
    
    @Autowired
    private MentorRepository mentorRepository;
    
    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private MeetingRepository meetingRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Mentor createMentor(Mentor mentor) {
        mentor.setPassword(passwordEncoder.encode(mentor.getPassword()));
        return mentorRepository.save(mentor);
    }

    @Override
    @Transactional
    public Mentor updateMentor(String id, Mentor mentorDetails) {
        Mentor mentor = getMentorById(id);
        mentor.setFirstName(mentorDetails.getFirstName());
        mentor.setLastName(mentorDetails.getLastName());
        mentor.setEmail(mentorDetails.getEmail());
        mentor.setPhoneNumber(mentorDetails.getPhoneNumber());
        mentor.setDepartment(mentorDetails.getDepartment());
        mentor.setSpecialization(mentorDetails.getSpecialization());
        mentor.setDesignation(mentorDetails.getDesignation());
        mentor.setYearsOfExperience(mentorDetails.getYearsOfExperience());
        mentor.setMaxStudents(mentorDetails.getMaxStudents());
        return mentorRepository.save(mentor);
    }

    @Override
    @Transactional
    public void deleteMentor(String id) {
        Mentor mentor = getMentorById(id);
        mentorRepository.delete(mentor);
    }

    @Override
    @Transactional(readOnly = true)
    public Mentor getMentorById(String id) {
        return mentorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mentor", "id", id));
    }

    @Override
    @Transactional(readOnly = true)
    public Mentor getMentorByUsername(String username) {
        return mentorRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Mentor", "username", username));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Mentor> getAllMentors() {
        return mentorRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> getMentorStudents(String mentorId) {
        return studentRepository.findByAssignedMentorId(mentorId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Meeting> getMentorMeetings(String mentorId) {
        return meetingRepository.findByMentorId(mentorId);
    }

    @Override
    @Transactional(readOnly = true)
    public Mentor getCurrentMentor() {
        // This will be implemented using SecurityContext in the actual implementation
        throw new UnsupportedOperationException("Method not implemented yet");
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasAvailableSlots(String mentorId) {
        Mentor mentor = getMentorById(mentorId);
        return getAssignedStudentsCount(mentorId) < mentor.getMaxStudents();
    }

    @Override
    @Transactional(readOnly = true)
    public int getAssignedStudentsCount(String mentorId) {
        return studentRepository.findByAssignedMentorId(mentorId).size();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Mentor> getAllMentors(int page, int size) {
        return mentorRepository.findAll(PageRequest.of(page, size));
    }
} 