package com.mahesh.mentee_connect.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.mahesh.mentee_connect.model.Mentor;
import com.mahesh.mentee_connect.model.Response;
import com.mahesh.mentee_connect.model.Student;
import com.mahesh.mentee_connect.payload.request.MentorAssignRequest;
import com.mahesh.mentee_connect.service.MentorService;
import com.mahesh.mentee_connect.service.StudentService;
import com.mahesh.mentee_connect.dto.StudentDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/admin")
@Tag(name = "Admin", description = "Admin management APIs")
@SecurityRequirement(name = "bearerAuth")
public class AdminController {

    @Autowired
    private StudentService studentService;
    
    @Autowired
    private MentorService mentorService;

    // Student Management
    @PostMapping("/students")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(studentService.createStudent(student));
    }

    @Operation(summary = "Get all students", description = "Retrieve a list of all students")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = StudentDTO.class)))),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden - Requires admin role")
    })
    @GetMapping("/students")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        List<StudentDTO> students = studentService.getAllStudentsAsDTO();
        return ResponseEntity.ok(students);
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
}