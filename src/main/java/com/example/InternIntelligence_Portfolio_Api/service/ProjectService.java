package com.example.InternIntelligence_Portfolio_Api.service;

import com.example.InternIntelligence_Portfolio_Api.dto.ProjectRequestDTO;
import com.example.InternIntelligence_Portfolio_Api.dto.ProjectResponseDTO;
import com.example.InternIntelligence_Portfolio_Api.exceptions.NotFoundException;
import com.example.InternIntelligence_Portfolio_Api.model.Project;
import com.example.InternIntelligence_Portfolio_Api.repository.ProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;

    public List<ProjectResponseDTO> getProjects(){
        List<Project> projects = projectRepository.findAll();
        return projects.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public ProjectResponseDTO getProject(Long id){
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Project with id:" + id + " not found"));
        return convertToResponseDTO(project);
    }

    public ProjectResponseDTO addProject(ProjectRequestDTO projectRequestDTO){
        Project project = convertToEntity(projectRequestDTO);
        project = projectRepository.save(project);
        return convertToResponseDTO(project);
    }

    public ProjectResponseDTO updateProject(Long id, ProjectRequestDTO projectRequestDTO){
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Project with id:" + id + " not found"));

        project.setName(projectRequestDTO.getName());
        project.setDescription(projectRequestDTO.getDescription());
        project = projectRepository.save(project);
        return convertToResponseDTO(project);
    }

    public void deleteProject(Long id){
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Project with id:" + id + " not found"));
        projectRepository.delete(project);
    }

    private ProjectResponseDTO convertToResponseDTO(Project project) {
        return new ProjectResponseDTO(project.getId(), project.getName(), project.getDescription());
    }

    private Project convertToEntity(ProjectRequestDTO projectRequestDTO){
        return new Project(projectRequestDTO.getName(),projectRequestDTO.getDescription());
    }

}