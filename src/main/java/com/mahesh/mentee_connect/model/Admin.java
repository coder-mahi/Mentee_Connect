// Admin.java
package com.mahesh.mentee_connect.model;

import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Document(collection = "admins")
public class Admin extends User {
    private String adminId;
    private String department;
    private String position;
    
    public Admin(String username, String email, String password, String firstName, 
                String lastName, String phoneNumber, String adminId, 
                String department, String position) {
        super();
        this.setUsername(username);
        this.setEmail(email);
        this.setPassword(password);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setPhoneNumber(phoneNumber);
        this.setRole(UserRole.ROLE_ADMIN);
        this.adminId = adminId;
        this.department = department;
        this.position = position;
    }
}