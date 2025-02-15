package com.example.InternIntelligence_Portfolio_Api.repository;

import com.example.InternIntelligence_Portfolio_Api.model.Skill;
import com.example.InternIntelligence_Portfolio_Api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillRepository extends JpaRepository<Skill,Long> {
    List<Skill> findByUser(User user);
}
