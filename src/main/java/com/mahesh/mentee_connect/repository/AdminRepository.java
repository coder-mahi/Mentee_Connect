// AdminRepository.java
package com.mahesh.mentee_connect.repository;

import com.mahesh.mentee_connect.model.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;
import java.util.List;

public interface AdminRepository extends MongoRepository<Admin, String> {
    Optional<Admin> findByEmail(String email);
    Optional<Admin> findByUsername(String username);
    Optional<Admin> findByAdminId(String adminId);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByAdminId(String adminId);
    List<Admin> findByFirstNameContainingOrLastNameContainingOrEmailContaining(
        String firstName, String lastName, String email);
}