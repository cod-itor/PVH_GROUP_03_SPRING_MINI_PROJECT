package org.ksga.pvh_group_03_spring_mini_project.service.impl;

import lombok.RequiredArgsConstructor;
import org.ksga.pvh_group_03_spring_mini_project.exception.NotFoundException;
import org.ksga.pvh_group_03_spring_mini_project.helper.AuthUtils;
import org.ksga.pvh_group_03_spring_mini_project.model.entity.Achievement;
import org.ksga.pvh_group_03_spring_mini_project.model.entity.AppUser;
import org.ksga.pvh_group_03_spring_mini_project.model.response.ApiResponse;
import org.ksga.pvh_group_03_spring_mini_project.model.response.AppUserResponse;
import org.ksga.pvh_group_03_spring_mini_project.repository.AchievementRepository;
import org.ksga.pvh_group_03_spring_mini_project.repository.AppUserRepository;
import org.ksga.pvh_group_03_spring_mini_project.service.AchievementService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AchievementServiceImpl implements AchievementService {

    private final AchievementRepository achievementRepository;
    private final AuthUtils authUtils;
    private final AppUserRepository appUserRepository;

    @Override
    public List<Achievement> getAllAchievement(Integer page, Integer size) {

        int offset =  size * (page - 1);
        return achievementRepository.getAllAchievement(offset, size);
    }

    @Override
    public List<Achievement> getUserAchievements(Integer page, Integer size) {

        int offset = size * (page - 1);
        UUID appUserId = authUtils.getCurrentUserIdentifier();

        AppUser appUser = appUserRepository.findUserByUUID(appUserId);

        if (appUser == null) {
            throw new NotFoundException("User not found!");
        }

        return achievementRepository.getUserAchievements(appUserId, offset, size);
    }

}
