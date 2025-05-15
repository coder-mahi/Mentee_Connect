package com.mahesh.mentee_connect.repository;

import com.mahesh.mentee_connect.model.Mentor;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MentorRepository extends MongoRepository<Mentor, String>{
	Optional<Mentor> findByEmail(String email);
    Optional<Mentor> findByName(String name);
}
