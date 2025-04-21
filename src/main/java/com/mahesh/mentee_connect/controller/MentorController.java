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

    @PostMapping
    public Mentor addMentor(@RequestBody Mentor mentor) {
        return mentorService.saveMentor(mentor);
    }

    @GetMapping
    public List<Mentor> getAllMentors() {
        return mentorService.getAllMentors();
    }

    @GetMapping("/{id}")
    public Mentor getMentor(@PathVariable Long id) {
        return mentorService.getMentorById(id);
    }
}
