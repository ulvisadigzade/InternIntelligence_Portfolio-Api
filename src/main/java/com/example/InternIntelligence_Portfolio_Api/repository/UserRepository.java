package com.example.InternIntelligence_Portfolio_Api.repository;


import com.example.InternIntelligence_Portfolio_Api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
}