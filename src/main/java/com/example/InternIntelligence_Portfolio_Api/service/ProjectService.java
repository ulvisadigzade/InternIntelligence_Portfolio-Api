package com.example.InternIntelligence_Portfolio_Api.service;


import com.example.InternIntelligence_Portfolio_Api.dto.ProjectDTO;
import com.example.InternIntelligence_Portfolio_Api.model.Project;
import com.example.InternIntelligence_Portfolio_Api.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    private final ProjectRepository projectrepository;

    public ProjectService(ProjectRepository projectrepository){
        this.projectrepository=projectrepository;
    }

    public List<ProjectDTO> getProjects(){
        List<Project> projects = projectrepository.findAll();
        return projects.stream()
                .map(this::convertToDTO) // Converting each model to DTO
                .collect(Collectors.toList());
    }

    public ProjectDTO getProject(Long id){
        Optional<Project> projectOptional = projectrepository.findById(id);
        Project project = projectOptional.get();
        return new ProjectDTO(project.getId(), project.getName(),project.getDescription());
    }

    //converting model to dto object
    private ProjectDTO convertToDTO(Project project) {
        return new ProjectDTO(project.getId(), project.getName(), project.getDescription());
    }

    public ProjectDTO addProject(ProjectDTO projectDTO){
        Project project = new Project(projectDTO.getName(), projectDTO.getDescription());
        projectrepository.save(project);
        return new ProjectDTO(project.getId(),project.getName(),project.getDescription());
    }

    public boolean deleteProject(Long id){
        if(projectrepository.existsById(id)){
            projectrepository.deleteById(id);
            return true;
        }
        else{
            return false;
        }
    }
}
