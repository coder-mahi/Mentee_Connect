package com.mahesh.mentee_connect.controller;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mahesh.mentee_connect.service.AdminService;
import com.mahesh.mentee_connect.service.StudentService;
import com.mahesh.mentee_connect.model.*;
import com.mahesh.mentee_connect.payload.MentorAssignRequest;
@RestController
@RequestMapping("/api/admin")
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private StudentService studentService;
	
	@GetMapping
	public Admin getAdmin(){
		return adminService.getAdmin();
	}
	
	@PostMapping
	public Admin addAdmin(@RequestBody Admin admin){
	    System.out.println("Received Admin: " + admin); // Log the object
	    return adminService.addAdmin(admin);
	}
	
	@PutMapping("/{id}")
	public Admin updateAdmin(@PathVariable ObjectId id, @RequestBody Admin admin) {
		return adminService.updateAdmin(id,admin);
	}
	
	
	 @PostMapping("/assign-mentor")
	    public ResponseEntity<Response> assignMentor(@RequestBody MentorAssignRequest request) {
	        boolean assigned = studentService.assignMentor(request.getStudentEmail(), request.getMentorName());

	        if (assigned) {
	            return ResponseEntity.ok(new Response("Mentor assigned successfully", true));
	        } else {
	            return ResponseEntity.status(404).body(new Response("Student not found with given email", false));
	        }
	    }
	 
	 
	 @GetMapping("/students")
	 public ResponseEntity<List<Student>> getAllStudents() {
	     List<Student> students = studentService.getAllStudents();
	     return ResponseEntity.ok(students);
	 }
}
