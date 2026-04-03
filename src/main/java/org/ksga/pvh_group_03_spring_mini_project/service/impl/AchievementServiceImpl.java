package org.ksga.pvh_group_03_spring_mini_project.service.impl;

import lombok.RequiredArgsConstructor;
import org.ksga.pvh_group_03_spring_mini_project.helper.AuthUtils;
import org.ksga.pvh_group_03_spring_mini_project.repository.AchievementRepository;
import org.ksga.pvh_group_03_spring_mini_project.repository.AppUserRepository;
import org.ksga.pvh_group_03_spring_mini_project.service.AchievementService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AchievementServiceImpl implements AchievementService {

    private final AchievementRepository achievementRepository;
    private final AuthUtils authUtils;
    private final AppUserRepository appUserRepository;

}
