package com.example.InternIntelligence_Portfolio_Api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;


@AllArgsConstructor
@Getter
@Setter
public class ProjectDTO {
    private Long id;

    @NotBlank(message="Name cannot be blank")
    @Size(min=5,max=50,message="Name must be between 5 and 50 characters")
    private String name;

    @NotBlank(message="Description cannot be blank")
    @Size(min=10,max=100,message="Name must be between 10 and 100 characters")
    private String description;

}
