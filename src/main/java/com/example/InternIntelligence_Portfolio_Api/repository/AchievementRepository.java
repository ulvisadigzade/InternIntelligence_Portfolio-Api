package com.example.InternIntelligence_Portfolio_Api.repository;


import com.example.InternIntelligence_Portfolio_Api.model.Achievement;
import com.example.InternIntelligence_Portfolio_Api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AchievementRepository extends JpaRepository<Achievement,Long> {
    List<Achievement> findByUser(User user);
}
