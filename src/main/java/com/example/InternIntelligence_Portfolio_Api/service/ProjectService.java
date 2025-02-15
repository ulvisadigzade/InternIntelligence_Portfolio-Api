package com.example.InternIntelligence_Portfolio_Api.service;

import com.example.InternIntelligence_Portfolio_Api.dto.ProjectRequestDTO;
import com.example.InternIntelligence_Portfolio_Api.dto.ProjectResponseDTO;
import com.example.InternIntelligence_Portfolio_Api.exceptions.NotFoundException;
import com.example.InternIntelligence_Portfolio_Api.model.Project;
import com.example.InternIntelligence_Portfolio_Api.model.User;
import com.example.InternIntelligence_Portfolio_Api.model.UserPrincipal;
import com.example.InternIntelligence_Portfolio_Api.repository.ProjectRepository;
import com.example.InternIntelligence_Portfolio_Api.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public List<ProjectResponseDTO> getProjects(){
        User user = GetUser();
        List<Project> projects = projectRepository.findByUser(user);

        return projects.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public ProjectResponseDTO getProject(Long id){
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Project with id:" + id + " not found"));

        User user = GetUser();

        if(!project.getUser().getEmail().equals(user.getEmail())){
            throw new AccessDeniedException("You don't have permission to access this project.");
        }

        return convertToResponseDTO(project);
    }

    public ProjectResponseDTO addProject(ProjectRequestDTO projectRequestDTO){
        Project project = convertToEntity(projectRequestDTO);

        User user = GetUser();
        project.setUser(user);

        project = projectRepository.save(project);
        return convertToResponseDTO(project);
    }

    public ProjectResponseDTO updateProject(Long id, ProjectRequestDTO projectRequestDTO){
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Project with id:" + id + " not found"));

        User user = GetUser();

        if(!project.getUser().getEmail().equals(user.getEmail())){
            throw new AccessDeniedException("You don't have permission to access this project.");
        }
        
        project.setName(projectRequestDTO.getName());
        project.setDescription(projectRequestDTO.getDescription());
        project = projectRepository.save(project);
        return convertToResponseDTO(project);
    }

    public void deleteProject(Long id){
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Project with id:" + id + " not found"));

        User user = GetUser();

        if(!project.getUser().getEmail().equals(user.getEmail())){
            throw new AccessDeniedException("You don't have permission to access this project.");
        }
        projectRepository.delete(project);
    }

    private ProjectResponseDTO convertToResponseDTO(Project project) {
        return new ProjectResponseDTO(project.getId(), project.getName(), project.getDescription());
    }

    private Project convertToEntity(ProjectRequestDTO projectRequestDTO){
        return new Project(projectRequestDTO.getName(),projectRequestDTO.getDescription());
    }

    private User GetUser() {
        UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByEmail(currentUser.getUsername())
                .orElseThrow(() -> new NotFoundException("User not found"));
    }
}