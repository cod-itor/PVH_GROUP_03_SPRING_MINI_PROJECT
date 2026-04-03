package org.ksga.pvh_group_03_spring_mini_project.service.impl;

import lombok.AllArgsConstructor;
import org.ksga.pvh_group_03_spring_mini_project.exception.HabitLogStatusException;
import org.ksga.pvh_group_03_spring_mini_project.exception.NotFoundException;
import org.ksga.pvh_group_03_spring_mini_project.model.entity.HabitLog;
import org.ksga.pvh_group_03_spring_mini_project.model.request.HabitLogRequest;
import org.ksga.pvh_group_03_spring_mini_project.repository.HabitLogRepository;
import org.ksga.pvh_group_03_spring_mini_project.service.HabitLogService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class HabitLogServiceImpl implements HabitLogService {

    private final HabitLogRepository habitLogRepository;

    @Override
    public HabitLog createHabitLog(HabitLogRequest habitLogRequest) {
        // Validate habit log status
        String status = habitLogRequest.getStatus().toUpperCase();
        if (!isValidStatus(status)) {
            throw new HabitLogStatusException("Invalid status: " + status + ". Allowed values: COMPLETED, SKIPPED, PENDING");
        }
        
        UUID habitId = habitLogRequest.getHabitId();
        if (habitId == null) {
            throw new HabitLogStatusException("Habit ID cannot be null");
        }
        
        // Create habit log entity
        UUID habitLogId = UUID.randomUUID();
        HabitLog habitLog = HabitLog.builder()
                .habitLogId(habitLogId)
                .logDate(LocalDateTime.now())
                .status(status)
                .HabitId(habitId)
                .xpEarned(0) // XP will be handled by achievement system
                .build();
        
        // Insert into database
        habitLogRepository.insertHabitLogRecord(habitLog);
        
        // Fetch and return the created habit log with full details
        HabitLog createdLog = habitLogRepository.findByHabitLogId(habitLogId);
        if (createdLog == null) {
            throw new NotFoundException("Failed to create habit log");
        }
        
        return createdLog;
    }

    @Override
    public List<HabitLog> getHabitLogsByHabitId(UUID habitId) {
        // Verify habit exists
        if (habitId == null) {
            throw new HabitLogStatusException("Habit ID cannot be null");
        }
        
        // Fetch habit logs
        List<HabitLog> habitLogs = habitLogRepository.findByHabitId(habitId);
        
        if (habitLogs == null || habitLogs.isEmpty()) {
            throw new NotFoundException("No habit logs found for habit ID: " + habitId);
        }
        
        return habitLogs;
    }
    
    // Helper method to validate status
    private boolean isValidStatus(String status) {
        return status.equals("COMPLETED") || status.equals("SKIPPED") || status.equals("PENDING");
    }
}
