package com.example.InternIntelligence_Portfolio_Api.controller;


import com.example.InternIntelligence_Portfolio_Api.dto.LoginRequest;
import com.example.InternIntelligence_Portfolio_Api.model.User;
import com.example.InternIntelligence_Portfolio_Api.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid User user){
        userService.saveUser(user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequest loginRequest){
        String token = userService.login(loginRequest);
        return ResponseEntity.ok().body(token);
    }

}
