package com.mahesh.mentee_connect.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.mahesh.mentee_connect.model.*;
import com.mahesh.mentee_connect.repository.AdminRepository;

@Service
public class AdminService {
	@Autowired
	private AdminRepository adminRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public Admin getAdmin(){
		return adminRepository.findAll().stream().findFirst().orElse(null);
	}
	
	public Admin updateAdmin(ObjectId id, Admin admin) {
		admin.setId(id);
		 if (admin.getPassword() != null && !admin.getPassword().isEmpty()) {
			 admin.setPassword(passwordEncoder.encode(admin.getPassword()));
	        }
		 
		return adminRepository.save(admin);
	}
	
	public Admin addAdmin(Admin admin) {
		 if(admin.getPassword() != null && !admin.getPassword().isEmpty()) {
			 admin.setPassword(passwordEncoder.encode(admin.getPassword()));
	        }
		return adminRepository.save(admin);
	}

}
