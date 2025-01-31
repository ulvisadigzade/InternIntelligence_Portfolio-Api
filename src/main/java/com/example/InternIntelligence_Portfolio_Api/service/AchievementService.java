package com.example.InternIntelligence_Portfolio_Api.service;

import com.example.InternIntelligence_Portfolio_Api.dto.AchievementRequestDTO;
import com.example.InternIntelligence_Portfolio_Api.dto.AchievementResponseDTO;
import com.example.InternIntelligence_Portfolio_Api.exceptions.NotFoundException;
import com.example.InternIntelligence_Portfolio_Api.model.Achievement;
import com.example.InternIntelligence_Portfolio_Api.repository.AchievementRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AchievementService {
    private final AchievementRepository achievementRepository;

    public List<AchievementResponseDTO> getAchievements(){
        List<Achievement> achievements = achievementRepository.findAll();
        return achievements.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public AchievementResponseDTO getAchievement(Long id){
        Achievement achievement = achievementRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Achievement with id:" + id + " not found"));
        return convertToResponseDTO(achievement);
    }

    public AchievementResponseDTO addAchievement(AchievementRequestDTO achievementRequestDTO){
        Achievement achievement = convertToEntity(achievementRequestDTO);
        achievement = achievementRepository.save(achievement);
        return convertToResponseDTO(achievement);
    }

    public AchievementResponseDTO updateAchievement(Long id,AchievementRequestDTO achievementRequestDTO){
        Achievement achievement = achievementRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Achievement with id:" + id + " not found"));

        achievement.setName(achievementRequestDTO.getName());
        achievement.setDescription(achievementRequestDTO.getDescription());
        achievement = achievementRepository.save(achievement);
        return convertToResponseDTO(achievement);
    }

    public void deleteAchievement(Long id){
        Achievement achievement = achievementRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Achievement with id:" + id + " not found"));

        achievementRepository.delete(achievement);
    }

    private AchievementResponseDTO convertToResponseDTO(Achievement achievement){
        return new AchievementResponseDTO(achievement.getId(),
                achievement.getName(),achievement.getDescription());
    }

    private Achievement convertToEntity(AchievementRequestDTO dto) {
        return new Achievement(dto.getName(),dto.getDescription());
    }

}
