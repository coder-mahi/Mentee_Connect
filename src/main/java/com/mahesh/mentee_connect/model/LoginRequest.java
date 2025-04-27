package com.mahesh.mentee_connect.model;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
