// --- REPOSITORY LAYER ---
package com.mahesh.mentee_connect.repository;

import com.mahesh.mentee_connect.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {}
