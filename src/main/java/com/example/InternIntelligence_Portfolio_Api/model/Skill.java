package com.example.InternIntelligence_Portfolio_Api.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="skills")
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Skill(String name){
        this.name=name;
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
