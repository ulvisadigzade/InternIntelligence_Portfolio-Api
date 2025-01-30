package com.example.InternIntelligence_Portfolio_Api.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class SkillDTO {
    private Long id;

    @NotBlank(message="Name cannot be blank")
    @Size(min=5,max=50,message="Name must be between 5 and 50 characters")
    private String name;
}
