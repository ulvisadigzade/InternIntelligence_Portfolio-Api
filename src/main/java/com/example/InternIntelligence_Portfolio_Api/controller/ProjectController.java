package com.example.InternIntelligence_Portfolio_Api.controller;

import com.example.InternIntelligence_Portfolio_Api.dto.ProjectRequestDTO;
import com.example.InternIntelligence_Portfolio_Api.dto.ProjectResponseDTO;
import com.example.InternIntelligence_Portfolio_Api.service.ProjectService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/projects")
@AllArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping
    public ResponseEntity<List<ProjectResponseDTO>> getProjects(){
        List<ProjectResponseDTO> projects = projectService.getProjects();
        return ResponseEntity.ok().body(projects);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> getProject(@PathVariable Long id){
        ProjectResponseDTO project = projectService.getProject(id);
        return ResponseEntity.ok().body(project);
    }

    @PostMapping
    public ResponseEntity<ProjectResponseDTO> addProject(@RequestBody @Valid ProjectRequestDTO projectRequestDTO){
        ProjectResponseDTO project = projectService.addProject(projectRequestDTO);
        return ResponseEntity.created(URI.create("/projects/" + project.getId())).body(project);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> updateProject(@PathVariable Long id, @RequestBody ProjectRequestDTO projectRequestDTO){
        ProjectResponseDTO project = projectService.updateProject(id, projectRequestDTO);
        return ResponseEntity.ok().body(project);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id){
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }


}