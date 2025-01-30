package com.example.InternIntelligence_Portfolio_Api.controller;

import com.example.InternIntelligence_Portfolio_Api.dto.ProjectRequestDTO;
import com.example.InternIntelligence_Portfolio_Api.dto.ProjectResponseDTO;
import com.example.InternIntelligence_Portfolio_Api.exceptions.ProjectNotFoundException;

import com.example.InternIntelligence_Portfolio_Api.service.ProjectService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/projects")
@AllArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping
    public List<ProjectResponseDTO> getProjects(){
        return projectService.getProjects();
    }

    @GetMapping("/{id}")
    public ProjectResponseDTO getProject(@PathVariable Long id){
        return projectService.getProject(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectResponseDTO addProject(@RequestBody @Valid ProjectRequestDTO projectRequestDTO){
        return projectService.addProject(projectRequestDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProject(@PathVariable Long id){
        boolean isDeleted = projectService.deleteProject(id);
        if(!isDeleted) {
            throw new ProjectNotFoundException("Project not found");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProject(@PathVariable Long id, @RequestBody ProjectRequestDTO projectRequestDTO){
        projectService.updateProject(id, projectRequestDTO);
        return ResponseEntity.ok("Updated successfully");
    }
}