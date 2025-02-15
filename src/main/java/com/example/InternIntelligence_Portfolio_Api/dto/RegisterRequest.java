package com.example.InternIntelligence_Portfolio_Api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "Email can't be blank")
    @Size(min = 3, max = 50, message = "Length of email must be in range [6,50]")
    private String email;

    @NotBlank(message = "Password can't be blank")
    @Size(min = 3, max = 50, message = "Length of password must be in range [3,50]")
    private String password;
}
