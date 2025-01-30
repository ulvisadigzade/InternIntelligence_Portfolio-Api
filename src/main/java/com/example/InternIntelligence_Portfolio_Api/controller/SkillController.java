package com.example.InternIntelligence_Portfolio_Api.controller;


import com.example.InternIntelligence_Portfolio_Api.dto.SkillDTO;
import com.example.InternIntelligence_Portfolio_Api.exceptions.ProjectNotFoundException;
import com.example.InternIntelligence_Portfolio_Api.model.Skill;
import com.example.InternIntelligence_Portfolio_Api.service.SkillService;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/skills")
@AllArgsConstructor
public class SkillController {
    private final SkillService skillservice;


    @GetMapping
    public List<SkillDTO> getSkills(){
        return skillservice.getSkills();
    }

    @GetMapping("/{id}")
    public SkillDTO getSkill(@PathVariable Long id){
        return skillservice.getSkill(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SkillDTO addSkill(@RequestBody SkillDTO skillDTO){
        SkillDTO savedSkillDTO = skillservice.addSkill(skillDTO);
        return savedSkillDTO;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSkill(Long id){
        boolean isDeleted = skillservice.deleteSkill(id);

        if(!isDeleted) {
            throw new ProjectNotFoundException("Project not found");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateSkill(@PathVariable Long id,@RequestBody SkillDTO skillDTO){
        boolean exists = skillservice.updateSkill(id,skillDTO);

        if(exists){
            return ResponseEntity.ok("Updated successfully");
        }
        return ResponseEntity.ok("Created successfully");
    }
}
