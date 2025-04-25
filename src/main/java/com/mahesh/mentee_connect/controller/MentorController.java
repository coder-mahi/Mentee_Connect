package com.mahesh.mentee_connect.controller;

import com.mahesh.mentee_connect.model.Mentor;
import com.mahesh.mentee_connect.service.MentorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mentors")
public class MentorController {

    @Autowired
    private MentorService mentorService;

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
}
