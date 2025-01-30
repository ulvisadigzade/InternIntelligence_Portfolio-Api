package com.example.InternIntelligence_Portfolio_Api.service;


import com.example.InternIntelligence_Portfolio_Api.dto.ProjectDTO;
import com.example.InternIntelligence_Portfolio_Api.model.Project;
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

    private final ProjectRepository projectrepository;


    public List<ProjectDTO> getProjects(){
        List<Project> projects = projectrepository.findAll();
        return projects.stream()
                .map(this::convertToDTO) // Converting each model to DTO
                .collect(Collectors.toList());
    }

    public ProjectDTO getProject(Long id){
        Project project = projectrepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found with id: " + id));
        return convertToDTO(project);
    }

    private ProjectDTO convertToDTO(Project project) {
        return new ProjectDTO(project.getId(), project.getName(), project.getDescription());
    }

    public ProjectDTO addProject(ProjectDTO projectDTO){
        Project project = new Project(projectDTO.getName(), projectDTO.getDescription());
        project = projectrepository.save(project);//save and get back
        return convertToDTO(project);
    }

    public boolean deleteProject(Long id){
        if(projectrepository.existsById(id)){
            projectrepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean updateProject(Long id,ProjectDTO projectDTO){
        if(projectrepository.existsById(id)){
            Project project = projectrepository.findById(id).get();
            project.setName(projectDTO.getName());
            project.setDescription(projectDTO.getDescription());
            projectrepository.save(project);
            return true;
        }
        addProject(projectDTO);
        return false;
    }
}
