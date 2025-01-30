package com.example.InternIntelligence_Portfolio_Api.controller;


import com.example.InternIntelligence_Portfolio_Api.dto.SkillRequestDTO;
import com.example.InternIntelligence_Portfolio_Api.dto.SkillResponseDTO;
import com.example.InternIntelligence_Portfolio_Api.exceptions.ProjectNotFoundException;
import com.example.InternIntelligence_Portfolio_Api.service.SkillService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/skills")
@AllArgsConstructor
public class SkillController {
    private final SkillService skillService;

    @GetMapping
    public List<SkillResponseDTO> getSkills(){
        return skillService.getSkills();
    }

    @GetMapping("/{id}")
    public SkillResponseDTO getSkills(@PathVariable Long id){
        return skillService.getSkill(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SkillResponseDTO addSkill(@RequestBody @Valid SkillRequestDTO skillRequestDTO){
        return skillService.addSkill(skillRequestDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSkill(@PathVariable Long id){
        boolean isDeleted = skillService.deleteSkill(id);
        if(!isDeleted) {
            throw new ProjectNotFoundException("Skill not found");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateSkill(@PathVariable Long id, @RequestBody SkillRequestDTO skillRequestDTO){
        skillService.updateSkill(id, skillRequestDTO);
        return ResponseEntity.ok("Updated successfully");
    }
}