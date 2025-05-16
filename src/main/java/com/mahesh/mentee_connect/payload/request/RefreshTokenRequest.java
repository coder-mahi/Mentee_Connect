package com.mahesh.mentee_connect.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
 
@Data
public class RefreshTokenRequest {
    @NotBlank
    private String refreshToken;
} 