package com.example.InternIntelligence_Portfolio_Api.controller;


import com.example.InternIntelligence_Portfolio_Api.dto.ProjectDTO;
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
    private final ProjectService projectservice;



    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProjectDTO> getProjects(){
        return projectservice.getProjects();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProjectDTO getProject(@PathVariable Long id){
        return projectservice.getProject(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectDTO addProject(@RequestBody @Valid ProjectDTO projectDTO){
        ProjectDTO savedProjectDTO = projectservice.addProject(projectDTO);
        return savedProjectDTO;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProject(@PathVariable Long id){
        boolean isDeleted = projectservice.deleteProject(id);

        if(!isDeleted) {
            throw new ProjectNotFoundException("Project not found");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateObject(@PathVariable Long id, @RequestBody ProjectDTO projectDTO){
        boolean exists = projectservice.updateProject(id,projectDTO);
        if(exists){
            return ResponseEntity.ok("Updated successfully");
        }
        return ResponseEntity.ok("New project created");
    }
}
