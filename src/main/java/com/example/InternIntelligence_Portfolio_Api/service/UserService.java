package com.example.InternIntelligence_Portfolio_Api.service;

import com.example.InternIntelligence_Portfolio_Api.dto.LoginRequest;
import com.example.InternIntelligence_Portfolio_Api.exceptions.NotFoundException;
import com.example.InternIntelligence_Portfolio_Api.model.User;
import com.example.InternIntelligence_Portfolio_Api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public void saveUser(User user){
        Optional<User> usr = userRepository.findByEmail(user.getEmail());
        if(usr.isPresent()){
            throw new NotFoundException("User with email: " + user.getEmail() + " already exists");
        }
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public String login(LoginRequest loginRequest){
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        if(!authentication.isAuthenticated()){
            throw new BadCredentialsException("Invalid credentials");
        }
        return jwtService.generateToken(loginRequest.getEmail());
    }

}
