package com.example.InternIntelligence_Portfolio_Api.controller;


import com.example.InternIntelligence_Portfolio_Api.dto.SkillRequestDTO;
import com.example.InternIntelligence_Portfolio_Api.dto.SkillResponseDTO;
import com.example.InternIntelligence_Portfolio_Api.service.SkillService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/skills")
@AllArgsConstructor
public class SkillController {
    private final SkillService skillService;

    @GetMapping
    public ResponseEntity<List<SkillResponseDTO>> getSkills(){
        List<SkillResponseDTO> skills = skillService.getSkills();
        return ResponseEntity.ok().body(skills);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SkillResponseDTO> getSkills(@PathVariable Long id){
        SkillResponseDTO skillResponseDTO = skillService.getSkill(id);
        return ResponseEntity.ok().body(skillResponseDTO);
    }

    @PostMapping
    public ResponseEntity<SkillResponseDTO> addSkill(@RequestBody @Valid SkillRequestDTO skillRequestDTO){
        SkillResponseDTO skill = skillService.addSkill(skillRequestDTO);
        return ResponseEntity.created(URI.create("/skills/" + skill.getId())).body(skill);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SkillResponseDTO> updateSkill(@PathVariable Long id, @RequestBody SkillRequestDTO skillRequestDTO){
        SkillResponseDTO skill = skillService.updateSkill(id, skillRequestDTO);
        return ResponseEntity.ok().body(skill);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSkill(@PathVariable Long id) {
        skillService.deleteSkill(id);
        return ResponseEntity.noContent().build();
    }

}