package com.example.InternIntelligence_Portfolio_Api.service;

import com.example.InternIntelligence_Portfolio_Api.dto.AchievementRequestDTO;
import com.example.InternIntelligence_Portfolio_Api.dto.AchievementResponseDTO;
import com.example.InternIntelligence_Portfolio_Api.exceptions.NotFoundException;
import com.example.InternIntelligence_Portfolio_Api.model.Achievement;
import com.example.InternIntelligence_Portfolio_Api.model.User;
import com.example.InternIntelligence_Portfolio_Api.model.UserPrincipal;
import com.example.InternIntelligence_Portfolio_Api.repository.AchievementRepository;
import com.example.InternIntelligence_Portfolio_Api.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AchievementService {
    private final AchievementRepository achievementRepository;
    private final UserRepository userRepository;


    public List<AchievementResponseDTO> getAchievements(){
        User user = GetUser();
        List<Achievement> achievements = achievementRepository.findByUser(user);
        return achievements.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public AchievementResponseDTO getAchievement(Long id){
        Achievement achievement = achievementRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Achievement with id:" + id + " not found"));

        User user = GetUser();

        if(!achievement.getUser().getEmail().equals(user.getEmail())){
            throw new AccessDeniedException("You don't have permission to access this achievement.");
        }

        return convertToResponseDTO(achievement);
    }

    public AchievementResponseDTO addAchievement(AchievementRequestDTO achievementRequestDTO){
        Achievement achievement = convertToEntity(achievementRequestDTO);

        User user = GetUser();
        achievement.setUser(user);

        achievement = achievementRepository.save(achievement);
        return convertToResponseDTO(achievement);
    }

    public AchievementResponseDTO updateAchievement(Long id,AchievementRequestDTO achievementRequestDTO){
        Achievement achievement = achievementRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Achievement with id:" + id + " not found"));

        User user = GetUser();

        if(!achievement.getUser().getEmail().equals(user.getEmail())){
            throw new AccessDeniedException("You don't have permission to access this achievement.");
        }

        achievement.setName(achievementRequestDTO.getName());
        achievement.setDescription(achievementRequestDTO.getDescription());
        achievement = achievementRepository.save(achievement);
        return convertToResponseDTO(achievement);
    }

    public void deleteAchievement(Long id){
        Achievement achievement = achievementRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Achievement with id:" + id + " not found"));

        User user = GetUser();

        if(!achievement.getUser().getEmail().equals(user.getEmail())){
            throw new AccessDeniedException("You don't have permission to access this project.");
        }
        achievementRepository.delete(achievement);
    }

    private AchievementResponseDTO convertToResponseDTO(Achievement achievement){
        return new AchievementResponseDTO(achievement.getId(),
                achievement.getName(),achievement.getDescription());
    }

    private Achievement convertToEntity(AchievementRequestDTO dto) {
        return new Achievement(dto.getName(),dto.getDescription());
    }

    private User GetUser() {
        UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByEmail(currentUser.getUsername())
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

}
