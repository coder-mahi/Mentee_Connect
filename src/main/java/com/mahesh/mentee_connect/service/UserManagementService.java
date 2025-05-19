package com.mahesh.mentee_connect.service;

import com.mahesh.mentee_connect.model.User;
import com.mahesh.mentee_connect.payload.request.UpdateProfileRequest;
import java.util.List;

public interface UserManagementService {
    List<User> getAllUsers();
    List<User> searchUsersByName(String searchTerm);
    void deleteUser(String userId);
    User updateUser(String userId, UpdateProfileRequest updateRequest);
    boolean isEmailTaken(String email);
} 