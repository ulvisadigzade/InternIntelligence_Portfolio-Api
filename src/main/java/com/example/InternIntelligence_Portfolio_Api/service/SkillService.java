package com.example.InternIntelligence_Portfolio_Api.service;


import com.example.InternIntelligence_Portfolio_Api.dto.SkillRequestDTO;
import com.example.InternIntelligence_Portfolio_Api.dto.SkillResponseDTO;
import com.example.InternIntelligence_Portfolio_Api.exceptions.NotFoundException;
import com.example.InternIntelligence_Portfolio_Api.model.Skill;
import com.example.InternIntelligence_Portfolio_Api.model.User;
import com.example.InternIntelligence_Portfolio_Api.model.UserPrincipal;
import com.example.InternIntelligence_Portfolio_Api.repository.SkillRepository;
import com.example.InternIntelligence_Portfolio_Api.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SkillService {
    private final SkillRepository skillrepository;
    private final UserRepository userRepository;

    public List<SkillResponseDTO> getSkills(){
        User user = GetUser();
        List<Skill>skills = skillrepository.findByUser(user);
        return skills.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public SkillResponseDTO getSkill(Long id){
        Skill skill = skillrepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Skill with id:" + id + " not found"));

        User user = GetUser();

        if(!skill.getUser().getEmail().equals(user.getEmail())){
            throw new AccessDeniedException("You don't have permission to access this skill.");
        }

        return convertToResponseDTO(skill);
    }

    public SkillResponseDTO addSkill(SkillRequestDTO skillRequestDTO){
        Skill skill = convertToEntity(skillRequestDTO);

        User user = GetUser();
        skill.setUser(user);

        skill = skillrepository.save(skill);
        return convertToResponseDTO(skill);
    }

    public Skill updateSkill(Long id, SkillRequestDTO skillRequestDTO){
        Skill skill = skillrepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Skill with id:" + id + " not found"));


        User user = GetUser();

        if(!skill.getUser().getEmail().equals(user.getEmail())){
            throw new AccessDeniedException("You don't have permission to access this skill.");
        }

        skill.setName(skillRequestDTO.getName());
        skill = skillrepository.save(skill);
        return skill;
    }

    public void deleteSkill(Long id){
        Skill skill = skillrepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Skill with id:" + id + " not found"));

        User user = GetUser();

        if(!skill.getUser().getEmail().equals(user.getEmail())){
            throw new AccessDeniedException("You don't have permission to access this skill.");
        }
        skillrepository.delete(skill);
    }

    private SkillResponseDTO convertToResponseDTO(Skill skill) {
        return new SkillResponseDTO(skill.getId(), skill.getName());
    }

    private Skill convertToEntity(SkillRequestDTO skillRequestDTO){
        return new Skill(skillRequestDTO.getName());
    }

    private User GetUser() {
        UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByEmail(currentUser.getUsername())
                .orElseThrow(() -> new NotFoundException("User not found"));
    }
}
