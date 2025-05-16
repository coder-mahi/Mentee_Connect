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

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private MentorRepository mentorRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = studentRepository.findByUsername(username)
                .map(student -> (User) student)
                .or(() -> mentorRepository.findByUsername(username)
                        .map(mentor -> (User) mentor))
                .or(() -> adminRepository.findByUsername(username)
                        .map(admin -> (User) admin));

        return user.map(UserDetailsImpl::build)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
    }
} 