package com.example.InternIntelligence_Portfolio_Api.repository;

import com.example.InternIntelligence_Portfolio_Api.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Long> {

}
