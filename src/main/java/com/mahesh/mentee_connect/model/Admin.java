package com.mahesh.mentee_connect.model;

import jakarta.persistence.Entity;
import lombok.*;

@NoArgsConstructor
// @AllArgsConstructor removed to avoid duplicate constructor
@Getter
@Setter
@Entity
public class Admin extends User {
    //additional if needed
}


