package com.example.InternIntelligence_Portfolio_Api.service;


import com.example.InternIntelligence_Portfolio_Api.dto.SkillRequestDTO;
import com.example.InternIntelligence_Portfolio_Api.dto.SkillResponseDTO;
import com.example.InternIntelligence_Portfolio_Api.exceptions.NotFoundException;
import com.example.InternIntelligence_Portfolio_Api.model.Skill;
import com.example.InternIntelligence_Portfolio_Api.repository.SkillRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SkillService {
    private final SkillRepository skillrepository;

    public List<SkillResponseDTO> getSkills(){
        List<Skill>skills = skillrepository.findAll();
        return skills.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public SkillResponseDTO getSkill(Long id){
            Skill skill = skillrepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Skill with id:" + id + " not found"));
        return convertToResponseDTO(skill);
    }

    public SkillResponseDTO addSkill(SkillRequestDTO skillRequestDTO){
        Skill skill = convertToEntity(skillRequestDTO);
        skill = skillrepository.save(skill);
        return convertToResponseDTO(skill);
    }

    public SkillResponseDTO updateSkill(Long id,SkillRequestDTO skillRequestDTO){
        Skill skill = skillrepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Skill with id:" + id + " not found"));

        skill.setName(skillRequestDTO.getName());
        skill = skillrepository.save(skill);
        return convertToResponseDTO(skill);
    }

    public void deleteSkill(Long id){
        Skill skill = skillrepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Skill with id:" + id + " not found"));
        skillrepository.delete(skill);
    }

    private SkillResponseDTO convertToResponseDTO(Skill skill) {
        return new SkillResponseDTO(skill.getId(), skill.getName());
    }

    private Skill convertToEntity(SkillRequestDTO skillRequestDTO){
        return new Skill(skillRequestDTO.getName());
    }
}
