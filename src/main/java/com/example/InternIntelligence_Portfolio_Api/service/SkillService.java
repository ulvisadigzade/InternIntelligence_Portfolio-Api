package com.example.InternIntelligence_Portfolio_Api.service;


import com.example.InternIntelligence_Portfolio_Api.dto.SkillRequestDTO;
import com.example.InternIntelligence_Portfolio_Api.dto.SkillResponseDTO;
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


    public List<SkillResponseDTO> getSkills(){
        List<Skill>skills = skillrepository.findAll();
        return skills.stream()
                .map(this::convertToResponseDTO) // Converting each model to DTO
                .collect(Collectors.toList());
    }

    public SkillResponseDTO getSkill(Long id){
        Skill skill = skillrepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Skill not found with id: " + id));
        return convertToResponseDTO(skill);
    }

    public SkillResponseDTO addSkill(SkillRequestDTO skillRequestDTO){
        Skill skill = new Skill(skillRequestDTO.getName());
        skill = skillrepository.save(skill);
        return convertToResponseDTO(skill);
    }

    public boolean deleteSkill(Long id){
        if(skillrepository.existsById(id)){
            skillrepository.deleteById(id);
            return true;
        }
        return true;
    }

    public void updateSkill(Long id,SkillRequestDTO skillRequestDTO){
        Skill skill = skillrepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Skill not found with id: " + id));

        skill.setName(skillRequestDTO.getName());
        skillrepository.save(skill);
    }
    private SkillResponseDTO convertToResponseDTO(Skill skill) {
        return new SkillResponseDTO(skill.getId(), skill.getName());
    }
}
