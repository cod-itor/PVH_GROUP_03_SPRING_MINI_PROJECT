package org.ksga.pvh_group_03_spring_mini_project.controller;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.ksga.pvh_group_03_spring_mini_project.service.impl.HabitLogServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/habit-log")
@SecurityRequirement(name = "bearerAuth")
public class HabitLogController {
    private final HabitLogServiceImpl habitLogService;


}