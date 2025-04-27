package com.mahesh.mentee_connect.controller;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mahesh.mentee_connect.service.AdminService;
import com.mahesh.mentee_connect.model.*;
@RestController
@RequestMapping("/api/admin")
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
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
	

	
}
