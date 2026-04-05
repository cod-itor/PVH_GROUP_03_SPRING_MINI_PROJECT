package org.ksga.pvh_group_03_spring_mini_project.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.ksga.pvh_group_03_spring_mini_project.model.entity.HabitLog;
import org.ksga.pvh_group_03_spring_mini_project.model.request.HabitLogRequest;
import org.ksga.pvh_group_03_spring_mini_project.model.response.ApiResponse;
import org.ksga.pvh_group_03_spring_mini_project.service.impl.HabitLogServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/habit-logs")
@SecurityRequirement(name = "bearerAuth")
public class HabitLogController {
    private final HabitLogServiceImpl habitLogService;

    @PostMapping
    public ResponseEntity<ApiResponse<HabitLog>> createHabitLog(@RequestBody @Valid HabitLogRequest habitLogRequest) {
        try {
            HabitLog habitLog = habitLogService.createHabitLog(habitLogRequest);
            ApiResponse<HabitLog> response = ApiResponse.<HabitLog>builder()
                    .success(true)
                    .message("Habit log created successfully!")
                    .status(HttpStatus.CREATED)
                    .payload(habitLog)
                    .build();
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            ApiResponse<HabitLog> errorResponse = ApiResponse.<HabitLog>builder()
                    .success(false)
                    .message(e.getMessage())
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @GetMapping("/{habit-id}")
    public ResponseEntity<ApiResponse<List<HabitLog>>> getHabitLogsByHabitId(   @Min(value = 1, message = "must be greater than 0")@RequestParam(defaultValue = "1") Integer page,
                                                                                  @Min(value = 1, message = "must be greater than 0")
                                                                                  @RequestParam(defaultValue = "10") Integer size,@PathVariable("habit-id") UUID habitId) {
        try {
            List<HabitLog> habitLogs = habitLogService.getHabitLogsByHabitId(habitId ,page ,size);
            ApiResponse<List<HabitLog>> response = ApiResponse.<List<HabitLog>>builder()
                    .success(true)
                    .message("Habit logs retrieved successfully!")
                    .status(HttpStatus.OK)
                    .payload(habitLogs)
                    .build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<List<HabitLog>> errorResponse = ApiResponse.<List<HabitLog>>builder()
                    .success(false)
                    .message(e.getMessage())
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
}