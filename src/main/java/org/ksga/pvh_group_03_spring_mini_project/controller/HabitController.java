package org.ksga.pvh_group_03_spring_mini_project.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.ksga.pvh_group_03_spring_mini_project.service.HabitService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/habits")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class HabitController {
    private final HabitService habitService;

}
