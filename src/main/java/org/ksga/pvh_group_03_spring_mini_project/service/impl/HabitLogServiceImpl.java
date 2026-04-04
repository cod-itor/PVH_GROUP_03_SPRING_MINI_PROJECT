package org.ksga.pvh_group_03_spring_mini_project.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ksga.pvh_group_03_spring_mini_project.exception.NotFoundException;
import org.ksga.pvh_group_03_spring_mini_project.helper.AuthUtils;
import org.ksga.pvh_group_03_spring_mini_project.model.entity.AppUser;
import org.ksga.pvh_group_03_spring_mini_project.model.entity.HabitLog;
import org.ksga.pvh_group_03_spring_mini_project.model.request.HabitLogRequest;
import org.ksga.pvh_group_03_spring_mini_project.repository.AchievementRepository;
import org.ksga.pvh_group_03_spring_mini_project.repository.AppUserRepository;
import org.ksga.pvh_group_03_spring_mini_project.repository.HabitLogRepository;
import org.ksga.pvh_group_03_spring_mini_project.service.HabitLogService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class HabitLogServiceImpl implements HabitLogService {

    private final HabitLogRepository habitLogRepository;
    private final AppUserRepository appUserRepository;
    private final AuthUtils authUtils;
    private final AchievementRepository achievementRepository;

    private static final int xpPerCompletion = 10;

    @Override
    public HabitLog createHabitLog(HabitLogRequest habitLogRequest) {
        UUID appUserId = authUtils.getCurrentUserIdentifier();
        
        AppUser appUser = appUserRepository.findUserByUUID(appUserId);
        if (appUser == null) {
            throw new NotFoundException("User not found!");
        }
        
        UUID habitId = habitLogRequest.getHabitId();
        if (habitId == null) {
            throw new NotFoundException("Habit ID cannot be null");
        }
        
        UUID habitLogId = UUID.randomUUID();
        HabitLog habitLog = HabitLog.builder()
                .habitLogId(habitLogId)
                .logDate(LocalDateTime.now())
                .status("COMPLETED")
                .HabitId(habitId)
                .xpEarned(xpPerCompletion)
                .build();
        
        habitLogRepository.insertHabitLogRecord(habitLog);
        
        int currentXp = appUser.getXp() != null ? appUser.getXp() : 0;
        int newXp = currentXp + xpPerCompletion;
        int newLevel = calculateLevel(newXp);
        appUserRepository.updateUserXp(appUserId, newXp, newLevel);
        
        HabitLog createdLog = habitLogRepository.findByHabitLogId(habitLogId);
        if (createdLog == null) {
            throw new NotFoundException("Failed to create habit log");
        }
        
        return createdLog;
    }

    @Override
    public List<HabitLog> getHabitLogsByHabitId(UUID habitId) {
        UUID appUserId = authUtils.getCurrentUserIdentifier();
        
        AppUser appUser = appUserRepository.findUserByUUID(appUserId);
        if (appUser == null) {
            throw new NotFoundException("User not found!");
        }
        
        if (habitId == null) {
            throw new NotFoundException("Habit ID cannot be null");
        }
        
        List<HabitLog> habitLogs = habitLogRepository.findByHabitId(habitId);
        
        if (habitLogs == null || habitLogs.isEmpty()) {
            throw new NotFoundException("No habit logs found for habit ID: " + habitId);
        }
        
        return habitLogs;
    }
    
    private int calculateLevel(int totalXp) {
        return (totalXp / 100) + 1;
    }
}
