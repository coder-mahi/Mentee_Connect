package com.mahesh.mentee_connect.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.mahesh.mentee_connect.model.Admin;
import com.mahesh.mentee_connect.repository.AdminRepository;
import com.mahesh.mentee_connect.model.User.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class DataInitializer implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        try {
            // Create default admin if not exists
            if (adminRepository.count() == 0) {
                logger.info("No admin user found. Creating default admin user...");
                
                Admin admin = new Admin();
                admin.setEmail("admin@menteeconnect.com");
                admin.setUsername("admin@menteeconnect.com");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setFirstName("Admin");
                admin.setLastName("User");
                admin.setRole(UserRole.ROLE_ADMIN);
                admin.setDepartment("Administration");
                admin.setPosition("System Administrator");
                
                adminRepository.save(admin);
                logger.info("Default admin user created successfully with email: {}", admin.getEmail());
            } else {
                logger.info("Admin user already exists. Skipping creation.");
            }
        } catch (Exception e) {
            logger.error("Error creating default admin user: {}", e.getMessage(), e);
        }
    }
}