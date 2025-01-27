package com.example.InternIntelligence_Portfolio_Api.controller;


import com.example.InternIntelligence_Portfolio_Api.dto.ProjectDTO;
import com.example.InternIntelligence_Portfolio_Api.service.ProjectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectService projectservice;

    @Autowired
    public ProjectController(ProjectService projectservice){
        this.projectservice=projectservice;
    }

    @GetMapping
    public List<ProjectDTO> getProjects(){
        return projectservice.getProjects();
    }

    @GetMapping("/{id}")
    public ProjectDTO getProject(@PathVariable Long id){
        return projectservice.getProject(id);
    }

    @PostMapping
    public ResponseEntity<ProjectDTO> addProject(@RequestBody ProjectDTO projectDTO){
        ProjectDTO savedProjectDTO = projectservice.addProject(projectDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProjectDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id){
        boolean isDeleted = projectservice.deleteProject(id);

        if(isDeleted){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
