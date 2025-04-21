package com.mahesh.mentee_connect.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.mahesh.mentee_connect.model.Mentor;
import com.mahesh.mentee_connect.service.MentorService;

@RestController
@RequestMapping("/api/mentors")
public class MentorController {

    @Autowired
    private MentorService mentorService;

    @PostMapping
    public @ResponseBody Mentor addMentor(@RequestBody Mentor mentor) {
        return mentorService.saveMentor(mentor);
    }

    @GetMapping
    public @ResponseBody List<Mentor> getAllMentors() {
        return mentorService.getAllMentors();
    }

    @GetMapping("/{id}")
    public @ResponseBody Mentor getMentor(@PathVariable Long id) {
        return mentorService.getMentorById(id);
    }

    @PutMapping("/{id}")
    public @ResponseBody Mentor updateMentor(@PathVariable Long id, @RequestBody Mentor mentor) {
        return mentorService.updateMentor(id, mentor);
    }

    @DeleteMapping("/{id}")
    public @ResponseBody String deleteMentor(@PathVariable Long id) {
        mentorService.deleteMentor(id);
        return "Mentor with ID " + id + " deleted successfully";
    }
}
