package com.mahesh.mentee_connect.repository;

import com.mahesh.mentee_connect.model.Mentor;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MentorRepository extends MongoRepository<Mentor, String>{
	Mentor findByEmail(String email);
	
}
