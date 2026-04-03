package org.ksga.pvh_group_03_spring_mini_project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.ksga.pvh_group_03_spring_mini_project.model.entity.Achievement;
import org.ksga.pvh_group_03_spring_mini_project.model.response.ApiResponse;
import org.ksga.pvh_group_03_spring_mini_project.service.AchievementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("api/v1/achievements")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class AchievementController {

    private final AchievementService achievementService;

    @Operation(summary = "Get user profile", description = "Fetches the details of the currently authenticated user.")
    @GetMapping
    public ResponseEntity<ApiResponse<List<Achievement>>> getAllAchievement (@RequestParam(defaultValue = "1")
                                                                                 @Min(value = 1, message = "Page number must be greater than 0") Integer page,
                                                                             @RequestParam(defaultValue = "10")
                                                                                 @Min(value = 1, message = "Page size must be greater than 0") Integer size) {
        ApiResponse<List<Achievement>> apiResponse = ApiResponse.<List<Achievement>>builder()
                .success(true)
                .message("Achievements retrieved successfully")
                .status(HttpStatus.OK)
                .payload(achievementService.getAllAchievement(page, size))
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @Operation(summary = "Get user achievements", description = "Fetches achievements earned by the currently authenticated user")
    @GetMapping("/{app-users}")
    public ResponseEntity<ApiResponse<List<Achievement>>> getUserAchievements(@RequestParam(defaultValue = "1")
                                                                              @Min(value = 1, message = "Page number must be greater than 0") Integer page,
                                                                              @RequestParam(defaultValue = "10")
                                                                              @Min(value = 1, message = "Page size must be greater than 0") Integer size) {
        ApiResponse<List<Achievement>> apiResponse = ApiResponse.<List<Achievement>>builder()
                .success(true)
                .message("Achievements for the specified App User retrieved successfully!")
                .status(HttpStatus.OK)
                .payload(achievementService.getUserAchievements(page, size))
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


}
