package com.example.InternIntelligence_Portfolio_Api.controller;


import com.example.InternIntelligence_Portfolio_Api.dto.AchievementRequestDTO;
import com.example.InternIntelligence_Portfolio_Api.dto.AchievementResponseDTO;
import com.example.InternIntelligence_Portfolio_Api.service.AchievementService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/achievements")
@AllArgsConstructor
public class AchievementController {
    private final AchievementService achievementService;

    @GetMapping
    public ResponseEntity<List<AchievementResponseDTO>>getAchievements(){
        List<AchievementResponseDTO> achievements = achievementService.getAchievements();
        return ResponseEntity.ok().body(achievements);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AchievementResponseDTO>getAchievement(@PathVariable Long id){
        AchievementResponseDTO achievement = achievementService.getAchievement(id);
        return ResponseEntity.ok().body(achievement);
    }

    @PostMapping
    public ResponseEntity<AchievementResponseDTO>addAchievement(@RequestBody @Valid AchievementRequestDTO achievementRequestDTO){
        AchievementResponseDTO achievement = achievementService.addAchievement(achievementRequestDTO);
        return ResponseEntity.created(URI.create("/achievements/" + achievement.getId())).body(achievement);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AchievementResponseDTO>updateAchievement(@PathVariable Long id,@RequestBody @Valid AchievementRequestDTO achievementRequestDTO){
        AchievementResponseDTO achievement = achievementService.updateAchievement(id,achievementRequestDTO);
        return ResponseEntity.ok().body(achievement);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>deleteAchievement(@PathVariable Long id){
        achievementService.deleteAchievement(id);
        return ResponseEntity.noContent().build();
    }
}
