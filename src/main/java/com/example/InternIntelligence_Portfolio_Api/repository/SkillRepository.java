package com.example.InternIntelligence_Portfolio_Api.repository;

import com.example.InternIntelligence_Portfolio_Api.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRepository extends JpaRepository<Skill,Long> {
}
