package com.example.InternIntelligence_Portfolio_Api.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    public Project(String name, String description) {
        this.name = name;
        this.description = description;
    }

}
