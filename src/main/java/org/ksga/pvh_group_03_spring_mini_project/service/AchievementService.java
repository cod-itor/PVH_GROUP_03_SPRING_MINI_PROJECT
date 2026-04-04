package org.ksga.pvh_group_03_spring_mini_project.service;


import org.ksga.pvh_group_03_spring_mini_project.model.entity.Achievement;
import org.ksga.pvh_group_03_spring_mini_project.model.response.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface AchievementService {


    List<Achievement> getAllAchievement(Integer page, Integer size);

    List<Achievement> getUserAchievements(Integer page, Integer size);
}
