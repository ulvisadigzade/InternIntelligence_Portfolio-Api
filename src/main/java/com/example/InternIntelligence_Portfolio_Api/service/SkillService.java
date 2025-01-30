package com.example.InternIntelligence_Portfolio_Api.service;


import com.example.InternIntelligence_Portfolio_Api.dto.ProjectDTO;
import com.example.InternIntelligence_Portfolio_Api.dto.SkillDTO;
import com.example.InternIntelligence_Portfolio_Api.model.Project;
import com.example.InternIntelligence_Portfolio_Api.model.Skill;
import com.example.InternIntelligence_Portfolio_Api.repository.SkillRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SkillService {

    private final SkillRepository skillrepository;


    public List<SkillDTO> getSkills(){
        List<Skill>skills = skillrepository.findAll();
        return skills.stream()
                .map(this::convertToDTO) // Converting each model to DTO
                .collect(Collectors.toList());
    }

    public SkillDTO getSkill(Long id){
        Skill skill = skillrepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Skill not found with id: " + id));
        return convertToDTO(skill);
    }
    public SkillDTO addSkill(SkillDTO skillDTO){
        Skill skill = new Skill(skillDTO.getName());
        skill = skillrepository.save(skill);
        return convertToDTO(skill);
    }
    private SkillDTO convertToDTO(Skill skill) {
        return new SkillDTO(skill.getId(), skill.getName());
    }

    public boolean deleteSkill(Long id){
        if(skillrepository.existsById(id)){
            skillrepository.deleteById(id);
            return true;
        }
        return true;
    }

    public boolean updateSkill(Long id,SkillDTO skillDTO){
        if(skillrepository.existsById(id)){
            Skill skill = skillrepository.findById(id).get();
            skill.setName(skillDTO.getName());
            skillrepository.save(skill);
            return true;
        }
        addSkill(skillDTO);
        return false;
    }
}
