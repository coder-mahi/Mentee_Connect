package com.mahesh.mentee_connect.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.mahesh.mentee_connect.model.Batch;
import com.mahesh.mentee_connect.model.Mentor;
import com.mahesh.mentee_connect.model.Response;
import com.mahesh.mentee_connect.model.ErrorResponse;
import com.mahesh.mentee_connect.model.Student;
import com.mahesh.mentee_connect.payload.request.MentorAssignRequest;
import com.mahesh.mentee_connect.service.BatchService;
import com.mahesh.mentee_connect.service.MentorService;
import com.mahesh.mentee_connect.service.StudentService;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private StudentService studentService;
    
    @Autowired
    private MentorService mentorService;
    
    @Autowired
    private BatchService batchService;

    // Student Management
    @PostMapping("/students")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(studentService.createStudent(student));
    }

    @GetMapping("/students")
    public ResponseEntity<?> getAllStudents(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        if (page != null && size != null) {
            return ResponseEntity.ok(studentService.getAllStudents(page, size));
        }
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @PutMapping("/students/{id}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable String id, 
            @RequestBody Student student) {
        return ResponseEntity.ok(studentService.updateStudent(id, student));
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable String id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    // Mentor Management
    @PostMapping("/mentors")
    public ResponseEntity<Mentor> createMentor(@RequestBody Mentor mentor) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mentorService.createMentor(mentor));
    }

    @GetMapping("/mentors")
    public ResponseEntity<Page<Mentor>> getAllMentors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(mentorService.getAllMentors(page, size));
    }

    @PostMapping("/assign-mentor")
    public ResponseEntity<?> assignMentor(@RequestBody MentorAssignRequest request) {
        Student updatedStudent = studentService.assignMentorToStudent(
            request.getStudentId(), 
            request.getMentorId()
        );
        return ResponseEntity.ok(new Response("Mentor assigned successfully", true));
    }

    // Batch Management
    @PostMapping("/batches")
    public ResponseEntity<Batch> createBatch(@RequestBody Batch batch) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(batchService.createBatch(batch));
    }

    @GetMapping("/batches/{id}/students")
    public ResponseEntity<List<Student>> getBatchStudents(@PathVariable String id) {
        return ResponseEntity.ok(batchService.getBatchStudents(id));
    }
}