package org.ksga.pvh_group_03_spring_mini_project.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ksga.pvh_group_03_spring_mini_project.exception.NotFoundException;
import org.ksga.pvh_group_03_spring_mini_project.helper.AuthUtils;
import org.ksga.pvh_group_03_spring_mini_project.model.entity.Achievement;
import org.ksga.pvh_group_03_spring_mini_project.model.entity.AppUser;
import org.ksga.pvh_group_03_spring_mini_project.model.entity.Habit;
import org.ksga.pvh_group_03_spring_mini_project.model.entity.HabitLog;
import org.ksga.pvh_group_03_spring_mini_project.model.request.HabitLogRequest;
import org.ksga.pvh_group_03_spring_mini_project.repository.AchievementRepository;
import org.ksga.pvh_group_03_spring_mini_project.repository.AppUserRepository;
import org.ksga.pvh_group_03_spring_mini_project.repository.HabitLogRepository;
import org.ksga.pvh_group_03_spring_mini_project.repository.UserAchievementRepository;
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
    private final UserAchievementRepository userAchievementRepository;

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
                .habitId(habitId)
                .xpEarned(xpPerCompletion)
                .build();

        habitLogRepository.insertHabitLogRecord(habitLog);

        int currentXp = appUser.getXp() != null ? appUser.getXp() : 0;
        int newXp = currentXp + xpPerCompletion;
        int newLevel = calculateLevel(newXp);

        appUserRepository.updateUserXp(appUserId, newXp, newLevel);

        checkAndAwardAchievements(appUserId, newXp);

        HabitLog createdLog = habitLogRepository.findByHabitLogId(habitLogId);
        if (createdLog == null) {
            throw new NotFoundException("Failed to create habit log");
        }

        return createdLog;
    }

    @Override
    public List<HabitLog> getHabitLogsByHabitId(UUID habitId,Integer page , Integer size) {
        UUID appUserId = authUtils.getCurrentUserIdentifier();

        AppUser appUser = appUserRepository.findUserByUUID(appUserId);
        if (appUser == null) {
            throw new NotFoundException("User not found!");
        }

        if (habitId == null) {
            throw new NotFoundException("Habit ID cannot be null");
        }
        int offset = size * (page - 1);

        List<HabitLog> habitLogs = habitLogRepository.findByHabitId(habitId ,offset ,size);

        if (habitLogs == null || habitLogs.isEmpty()) {
            throw new NotFoundException("No habit logs found for habit ID: " + habitId);
        }

        return habitLogs;
    }

    private int calculateLevel(int totalXp) {
        return (totalXp / 100);
    }

    private void checkAndAwardAchievements(UUID appUserId, int newXp) {
        log.debug("Checking achievements for user {} with new XP: {}", appUserId, newXp);

        List<Achievement> allAchievements = achievementRepository.getAllAchievement(0, Integer.MAX_VALUE);

        for (Achievement achievement : allAchievements) {
            int xpRequired = achievement.getXpRequired() != null ? achievement.getXpRequired() : 0;

            if (newXp >= xpRequired) {
                if (!userAchievementRepository.hasUserEarnedAchievement(appUserId, achievement.getAchievementId())) {
                    userAchievementRepository.insertUserAchievement(appUserId, achievement.getAchievementId());
                    log.info("Achievement awarded to user {}: {} (Required XP: {})",
                            appUserId, achievement.getTitle(), xpRequired);
                }
            }
        }
    }
}
