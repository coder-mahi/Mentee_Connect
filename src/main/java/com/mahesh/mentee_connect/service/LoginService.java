package com.mahesh.mentee_connect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mahesh.mentee_connect.model.Response;
import com.mahesh.mentee_connect.repository.AdminRepository;
import com.mahesh.mentee_connect.repository.MentorRepository;
import com.mahesh.mentee_connect.repository.StudentRepository;
import com.mahesh.mentee_connect.model.Admin;
import com.mahesh.mentee_connect.model.Mentor;
import com.mahesh.mentee_connect.model.Student;

@Service
public class LoginService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private MentorRepository mentorRepository;

    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    
    public Response login(String email, String password) {
    	//admin login
    	Admin admin = adminRepository.findByEmail(email);
        if (admin != null && passwordEncoder.matches(password,admin.getPassword())) {
            return new Response("Admin Login Successful", true);
        }

        //mentor login
        Mentor mentor = mentorRepository.findByEmail(email);
        if (mentor != null && passwordEncoder.matches(password, mentor.getPassword())) {
            return new Response("Mentor Login Successful", true);
        }

        //student login
        Student student = studentRepository.findByEmail(email);
        if (student != null && passwordEncoder.matches(password, student.getPassword())) {
            return new Response("Student Login Successful", true);
        }

        return new Response("Invalid Email or Password", false);
    }
}
