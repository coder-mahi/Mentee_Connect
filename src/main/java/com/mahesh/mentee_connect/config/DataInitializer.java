package com.mahesh.mentee_connect.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.mahesh.mentee_connect.model.Admin;
import com.mahesh.mentee_connect.repository.AdminRepository;
import com.mahesh.mentee_connect.model.User.UserRole;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Create default admin if not exists
        if (adminRepository.count() == 0) {
            Admin admin = new Admin();
            admin.setEmail("admin@menteeconnect.com");
            admin.setUsername("admin@menteeconnect.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setFirstName("Admin");
            admin.setLastName("User");
            admin.setRole(UserRole.ROLE_ADMIN);
            adminRepository.save(admin);
        }
    }
}