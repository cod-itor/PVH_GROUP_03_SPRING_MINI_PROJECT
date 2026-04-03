package org.ksga.pvh_group_03_spring_mini_project.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.ksga.pvh_group_03_spring_mini_project.service.AchievementService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/v1/achievements")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class AchievementController {

    private final AchievementService achievementService;

}
