package com.example.InternIntelligence_Portfolio_Api.service;

import com.example.InternIntelligence_Portfolio_Api.model.User;
import com.example.InternIntelligence_Portfolio_Api.model.UserPrincipal;
import com.example.InternIntelligence_Portfolio_Api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " not found"));
        
        return new UserPrincipal(user);
    }
}
