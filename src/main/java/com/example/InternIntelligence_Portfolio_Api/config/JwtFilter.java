package com.example.InternIntelligence_Portfolio_Api.config;

import com.example.InternIntelligence_Portfolio_Api.service.JwtService;
import com.example.InternIntelligence_Portfolio_Api.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final ApplicationContext context;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String email = null;

        if(authHeader==null && !authHeader.startsWith("Bearer ")){
            return;
        }
        token = authHeader.substring(7);
        email = jwtService.extractEmail(token);

        if(email == null){
            throw new RuntimeException("Email is null");
        }
        if(SecurityContextHolder.getContext().getAuthentication()!=null){
            filterChain.doFilter(request,response);
        }

        UserDetails userDetails = context.getBean(UserDetailsServiceImpl.class).loadUserByUsername(email);

        if(!jwtService.validateToken(token,userDetails)){
            return;
        }

        UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request,response);
    }
}
