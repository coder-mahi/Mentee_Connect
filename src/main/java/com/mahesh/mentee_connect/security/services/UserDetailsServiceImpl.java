package com.mahesh.mentee_connect.security.services;

import com.mahesh.mentee_connect.model.User;
import com.mahesh.mentee_connect.repository.StudentRepository;
import com.mahesh.mentee_connect.repository.MentorRepository;
import com.mahesh.mentee_connect.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private MentorRepository mentorRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        logger.debug("Attempting to load user by email: {}", email);
        
        Optional<User> user = studentRepository.findByEmail(email)
                .map(student -> (User) student)
                .or(() -> mentorRepository.findByEmail(email)
                        .map(mentor -> (User) mentor))
                .or(() -> adminRepository.findByEmail(email)
                        .map(admin -> (User) admin));

        if (user.isEmpty()) {
            logger.error("User not found with email: {}", email);
            throw new UsernameNotFoundException("User Not Found with email: " + email);
        }

        logger.debug("User found with email: {}, role: {}", email, user.get().getRole());
        return UserDetailsImpl.build(user.get());
    }
} 