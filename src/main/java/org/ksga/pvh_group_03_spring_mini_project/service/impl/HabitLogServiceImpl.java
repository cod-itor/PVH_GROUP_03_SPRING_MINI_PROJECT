package org.ksga.pvh_group_03_spring_mini_project.service.impl;

import lombok.AllArgsConstructor;
import org.ksga.pvh_group_03_spring_mini_project.helper.AuthUtils;
import org.ksga.pvh_group_03_spring_mini_project.repository.AchievementRepository;
import org.ksga.pvh_group_03_spring_mini_project.repository.AppUserRepository;
import org.ksga.pvh_group_03_spring_mini_project.repository.HabitLogRepository;
import org.ksga.pvh_group_03_spring_mini_project.repository.HabitRepository;
import org.ksga.pvh_group_03_spring_mini_project.service.HabitLogService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class HabitLogServiceImpl implements HabitLogService {

    private final HabitLogRepository habitLogRepository;
    private final AppUserRepository appUserRepository;
    private final HabitRepository habitRepository;
    private final AuthUtils authUtils;
    private final AchievementRepository achievementRepository;


}
