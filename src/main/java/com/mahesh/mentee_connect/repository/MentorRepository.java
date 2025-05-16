// MentorRepository.java
package com.mahesh.mentee_connect.repository;

import com.mahesh.mentee_connect.model.Mentor;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;
import java.util.List;

public interface MentorRepository extends MongoRepository<Mentor, String> {
    Optional<Mentor> findByEmail(String email);
    Optional<Mentor> findByUsername(String username);
    Optional<Mentor> findByMentorId(String mentorId);
    List<Mentor> findByDepartment(String department);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByMentorId(String mentorId);
}
