package com.mahesh.mentee_connect.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.mahesh.mentee_connect.model.Admin;

public interface AdminRepository extends MongoRepository<Admin, ObjectId> {
	Admin findByEmail(String email);
	
}
