package com.example.InternIntelligence_Portfolio_Api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import com.example.InternIntelligence_Portfolio_Api.dto.SkillRequestDTO;
import com.example.InternIntelligence_Portfolio_Api.dto.SkillResponseDTO;
import com.example.InternIntelligence_Portfolio_Api.model.Skill;
import com.example.InternIntelligence_Portfolio_Api.service.SkillService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/skills")
@RequiredArgsConstructor
public class SkillController {
    private final SkillService skillService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<SkillResponseDTO> getSkills(){
        return skillService.getSkills();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SkillResponseDTO getSkills(@PathVariable Long id){
        return skillService.getSkill(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public URI addSkill(@RequestBody @Valid SkillRequestDTO skillRequestDTO){
        SkillResponseDTO skill = skillService.addSkill(skillRequestDTO);
        return URI.create("/skills/" + skill.getId());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public URI updateSkill(
            @PathVariable Long id,
            @RequestBody SkillRequestDTO skillRequestDTO
    ) {
        Skill skill = skillService.updateSkill(id, skillRequestDTO);
        return URI.create("/skills/" + skill.getId());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSkill(@PathVariable Long id) {
        skillService.deleteSkill(id);
    }

}