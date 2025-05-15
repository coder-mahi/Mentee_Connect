package com.mahesh.mentee_connect.controller;

import com.mahesh.mentee_connect.exception.ResourceNotFoundException;
import com.mahesh.mentee_connect.model.Mentor;
import com.mahesh.mentee_connect.model.Student;
import com.mahesh.mentee_connect.repository.MentorRepository;
import com.mahesh.mentee_connect.repository.StudentRepository;
import com.mahesh.mentee_connect.service.MentorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import com.mahesh.dto.*;

@RestController
@RequestMapping("/api/mentors")
public class MentorController {

    @Autowired
    private MentorService mentorService;

    @Autowired
    private MentorRepository mentorRepository;

    @Autowired
    private StudentRepository studentRepository; // Changed from Student to StudentRepository

    @GetMapping
    public List<Mentor> getAllMentors() {
        return mentorService.getAllMentors();
    }

    @GetMapping("/{id}")
    public Mentor getMentor(@PathVariable String id) {
        return mentorService.getMentorById(id);
    }

    @PostMapping
    public Mentor createMentor(@RequestBody Mentor mentor) {
        return mentorService.addMentor(mentor);
    }

    @PutMapping("/{id}")
    public Mentor updateMentor(@PathVariable String id, @RequestBody Mentor mentor) {
        return mentorService.updateMentor(id, mentor);
    }

    @DeleteMapping("/{id}")
    public void deleteMentor(@PathVariable String id) {
        mentorService.deleteMentor(id);
    }

    @GetMapping("/{mentorId}/my-mentees")
    public ResponseEntity<List<StudentResponse>> getMyMentees(@PathVariable String mentorId) {
        Mentor mentor = mentorRepository.findById(mentorId)
            .orElseThrow(() -> new ResourceNotFoundException("Mentor not found with ID: " + mentorId));
        
        // Fetch all students allocated to the mentor
        List<Student> students = studentRepository.findByMentorName(mentor.getName());
        
        // Mapping students to a response DTO
        List<StudentResponse> response = students.stream()
            .map(student -> new StudentResponse(
                student.getName(),
                student.getEmail()
            ))
            .collect(Collectors.toList());
        
        return ResponseEntity.ok(response);
    }
     
}