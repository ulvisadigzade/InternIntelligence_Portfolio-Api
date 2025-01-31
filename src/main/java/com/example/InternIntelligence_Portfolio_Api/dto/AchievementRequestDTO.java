package com.example.InternIntelligence_Portfolio_Api.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AchievementRequestDTO {

    @NotBlank(message = "Name can't be blank")
    @Size(min = 3, max = 50, message = "Length of name must be in range [3,50]")
    private String name;

    @NotBlank(message = "Description can't be blank")
    @Size(min = 10, max = 50, message = "Description of name must be in range [10,50]")
    private String description;
}
