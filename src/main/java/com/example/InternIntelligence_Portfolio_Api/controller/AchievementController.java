package com.example.InternIntelligence_Portfolio_Api.controller;


import com.example.InternIntelligence_Portfolio_Api.dto.AchievementRequestDTO;
import com.example.InternIntelligence_Portfolio_Api.dto.AchievementResponseDTO;
import com.example.InternIntelligence_Portfolio_Api.service.AchievementService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
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
@RequestMapping("/achievements")
@AllArgsConstructor
public class AchievementController {
    private final AchievementService achievementService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AchievementResponseDTO> getAchievements(){
        return achievementService.getAchievements();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AchievementResponseDTO getAchievement(@PathVariable Long id){
        return achievementService.getAchievement(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public URI addAchievement(@RequestBody @Valid AchievementRequestDTO achievementRequestDTO){
        AchievementResponseDTO achievement = achievementService.addAchievement(achievementRequestDTO);
        return URI.create("/achievements/" + achievement.getId());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public URI updateAchievement(
            @PathVariable Long id,
            @RequestBody @Valid AchievementRequestDTO achievementRequestDTO
    ) {
        AchievementResponseDTO achievement = achievementService.updateAchievement(id,achievementRequestDTO);
        return URI.create("/achievements/" + achievement.getId());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Void deleteAchievement(@PathVariable Long id){
        achievementService.deleteAchievement(id);
        return null;
    }
}
