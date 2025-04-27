package com.mahesh.mentee_connect.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mahesh.mentee_connect.model.*;
import com.mahesh.mentee_connect.repository.AdminRepository;

@Service
public class AdminService {
	@Autowired
	private AdminRepository adminRepository;
	
	public Admin getAdmin(){
		return adminRepository.findAll().stream().findFirst().orElse(null);
	}
	
	public Admin updateAdmin(ObjectId id, Admin admin) {
		admin.setId(id);
		return adminRepository.save(admin);
	}
	
	public Admin addAdmin(Admin admin) {
		return adminRepository.save(admin);
	}

}
