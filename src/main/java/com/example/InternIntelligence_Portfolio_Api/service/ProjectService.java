package com.example.InternIntelligence_Portfolio_Api.service;

import com.example.InternIntelligence_Portfolio_Api.dto.ProjectRequestDTO;
import com.example.InternIntelligence_Portfolio_Api.dto.ProjectResponseDTO;
import com.example.InternIntelligence_Portfolio_Api.dto.SkillRequestDTO;
import com.example.InternIntelligence_Portfolio_Api.model.Project;
import com.example.InternIntelligence_Portfolio_Api.model.Skill;
import com.example.InternIntelligence_Portfolio_Api.repository.ProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;

    public List<ProjectResponseDTO> getProjects(){
        return projectRepository.findAll()
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public ProjectResponseDTO getProject(Long id){
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found with id: " + id));
        return convertToResponseDTO(project);
    }

    public ProjectResponseDTO addProject(ProjectRequestDTO projectRequestDTO){
        Project project = new Project(projectRequestDTO.getName(), projectRequestDTO.getDescription());
        project = projectRepository.save(project);
        return convertToResponseDTO(project);
    }

    public boolean deleteProject(Long id){
        if(projectRepository.existsById(id)){
            projectRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public void updateProject(Long id, ProjectRequestDTO projectRequestDTO){
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found with id: " + id));

        project.setName(projectRequestDTO.getName());
        project.setDescription(projectRequestDTO.getDescription());
        projectRepository.save(project);
    }

    private ProjectResponseDTO convertToResponseDTO(Project project) {
        return new ProjectResponseDTO(project.getId(), project.getName(), project.getDescription());
    }
}