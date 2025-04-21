package com.mahesh.mentee_connect.model;

// --- Mentor.java ---
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Mentor extends User {

    @OneToMany(mappedBy = "mentor", cascade = CascadeType.ALL)
    private List<Student> students;

    @ManyToOne
    private Batch batch;
}


