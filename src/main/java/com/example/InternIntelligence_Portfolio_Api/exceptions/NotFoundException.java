package com.example.InternIntelligence_Portfolio_Api.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class NotFoundException extends RuntimeException{
    private final String message;
}
