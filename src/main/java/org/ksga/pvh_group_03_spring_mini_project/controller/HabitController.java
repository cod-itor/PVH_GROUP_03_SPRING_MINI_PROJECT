package org.ksga.pvh_group_03_spring_mini_project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.ksga.pvh_group_03_spring_mini_project.helper.AuthUtils;
import org.ksga.pvh_group_03_spring_mini_project.model.entity.Habit;
import org.ksga.pvh_group_03_spring_mini_project.model.request.HabitRequest;
import org.ksga.pvh_group_03_spring_mini_project.model.response.ApiResponse;
import org.ksga.pvh_group_03_spring_mini_project.service.HabitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/habits")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class HabitController {
    private final HabitService habitService;


    @Operation(summary = "Get all habits")
    @GetMapping
    public ResponseEntity<ApiResponse<List<Habit>>> getAllHabits(
            @Min (value = 1, message = "must be greater than 0")@RequestParam(defaultValue = "1") Integer page,
            @Min(value = 1, message = "must be greater than 0")
            @RequestParam(defaultValue = "10") Integer size
    ) {
        List<Habit> habits = habitService.getAllHabits(page, size);

        return ResponseEntity.ok(ApiResponse.<List<Habit>>builder().
                success(true).
                status(HttpStatus.OK).
                message("Fetched all habits successfully!").
                payload(habits).
                timestamp(LocalDateTime.now()).build());
    }

    @Operation(summary = "Create a new habit")
    @PostMapping
    public ResponseEntity<ApiResponse<Habit>> createHabit(
            @Valid @RequestBody HabitRequest request
    ) {
        Habit newHabit = habitService.createHabit(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<Habit>builder().
                        success(true).
                        status(HttpStatus.CREATED).
                        message("A new habit created successfully!").
                        payload(newHabit).
                        timestamp(LocalDateTime.now()).build()
        );
    }

    @Operation(summary = "Get habit by ID")
    @GetMapping("/{habit-id}")
    public ResponseEntity<ApiResponse<Habit>> getHabitById (
            @PathVariable("habit-id") UUID habitId
    ) {
        Habit habit = habitService.getHabitById(habitId);

        return ResponseEntity.ok(ApiResponse.<Habit>builder().
                success(true).
                status(HttpStatus.OK).
                message("Habit with ID " + habitId + " found.").
                payload(habit).
                timestamp(LocalDateTime.now()).build()
        );
    }

    @Operation(summary = "Delete habit by ID")
    @DeleteMapping("/{habit-id}")
    public ResponseEntity<ApiResponse<Habit>> deleteHabitById (
            @PathVariable("habit-id") UUID habitId
    ) {
        habitService.deleteHabitById(habitId);

        return ResponseEntity.ok(ApiResponse.<Habit>builder().
                success(true).
                status(HttpStatus.OK).
                message("Habit with ID " + habitId + " deleted successfully.").
                timestamp(LocalDateTime.now()).build());
    }

    @Operation(summary = "Update habit by ID")
    @PutMapping("/{habit-id}")
    public ResponseEntity<ApiResponse<Habit>> updateHabitById (
            @PathVariable("habit-id") UUID habitId,
            @Valid @RequestBody HabitRequest request
    ) {
        Habit habit = habitService.updateHabitById(habitId, request);

        return ResponseEntity.ok(ApiResponse.<Habit>builder().
                success(true).
                status(HttpStatus.OK).
                message("Habit with ID " + habitId + " updated successfully.").
                payload(habit).
                timestamp(LocalDateTime.now()).build());
    }

}











