package com.mahesh.mentee_connect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.mahesh.mentee_connect.model.LoginRequest;
import com.mahesh.mentee_connect.model.Response;
import com.mahesh.mentee_connect.service.LoginService;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    private LoginService loginService;
    // Login API for Admin, Mentor, and Student
    
    @PostMapping
    public Response login(@RequestBody LoginRequest loginRequest) {
        return loginService.login(loginRequest.getEmail(), loginRequest.getPassword());
    }
}
