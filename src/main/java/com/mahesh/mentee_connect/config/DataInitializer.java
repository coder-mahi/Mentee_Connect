package com.mahesh.mentee_connect.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.mahesh.mentee_connect.model.Admin;
import com.mahesh.mentee_connect.model.Student;
import com.mahesh.mentee_connect.model.Mentor;
import com.mahesh.mentee_connect.repository.AdminRepository;
import com.mahesh.mentee_connect.repository.StudentRepository;
import com.mahesh.mentee_connect.repository.MentorRepository;
import com.mahesh.mentee_connect.model.User.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class DataInitializer implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    @Autowired
    private AdminRepository adminRepository;
    
    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private MentorRepository mentorRepository;

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
            
            // Create default student if not exists
            if (studentRepository.count() == 0) {
                logger.info("No student user found. Creating default student user...");
                
                Student student = new Student();
                student.setEmail("student@menteeconnect.com");
                student.setUsername("student@menteeconnect.com");
                student.setPassword(passwordEncoder.encode("student123"));
                student.setFirstName("Student");
                student.setLastName("User");
                student.setRole(UserRole.ROLE_STUDENT);
                student.setStudentId("S12345");
                student.setCourse("Computer Science");
                student.setBatch("2025");
                student.setSemester(4);
                
                studentRepository.save(student);
                logger.info("Default student user created successfully with email: {}", student.getEmail());
            } else {
                logger.info("Student user already exists. Skipping creation.");
            }
            
            // Create default mentor if not exists
            if (mentorRepository.count() == 0) {
                logger.info("No mentor user found. Creating default mentor user...");
                
                Mentor mentor = new Mentor();
                mentor.setEmail("mentor@menteeconnect.com");
                mentor.setUsername("mentor@menteeconnect.com");
                mentor.setPassword(passwordEncoder.encode("mentor123"));
                mentor.setFirstName("Mentor");
                mentor.setLastName("User");
                mentor.setRole(UserRole.ROLE_MENTOR);
                mentor.setMentorId("M12345");
                mentor.setDepartment("Computer Science");
                mentor.setSpecialization("Software Engineering");
                mentor.setDesignation("Professor");
                mentor.setYearsOfExperience(5);
                
                mentorRepository.save(mentor);
                logger.info("Default mentor user created successfully with email: {}", mentor.getEmail());
            } else {
                logger.info("Mentor user already exists. Skipping creation.");
            }
            
        } catch (Exception e) {
            logger.error("Error creating default users: {}", e.getMessage(), e);
        }
    }
}