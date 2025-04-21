package com.mahesh.mentee_connect.model;
// --- Batch.java ---
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Batch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String department;
    private int year;

    @OneToMany(mappedBy = "batch", cascade = CascadeType.ALL)
    private List<Mentor> mentors;

    @OneToMany(mappedBy = "batch", cascade = CascadeType.ALL)
    private List<Student> students;
}


