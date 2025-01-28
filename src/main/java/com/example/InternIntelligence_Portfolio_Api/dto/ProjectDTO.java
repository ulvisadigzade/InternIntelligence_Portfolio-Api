package com.example.InternIntelligence_Portfolio_Api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Component;

public class ProjectDTO {
    private Long id;

    @NotBlank(message="Name cannot be blank")
    @Size(min=5,max=50,message="Name must be between 5 and 50 characters")
    private String name;

    @NotBlank(message="Description cannot be blank")
    @Size(min=10,max=100,message="Name must be between 10 and 100 characters")
    private String description;

    public ProjectDTO(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
