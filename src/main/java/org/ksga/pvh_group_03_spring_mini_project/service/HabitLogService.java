package org.ksga.pvh_group_03_spring_mini_project.service;

import org.ksga.pvh_group_03_spring_mini_project.model.entity.HabitLog;
import org.ksga.pvh_group_03_spring_mini_project.model.request.HabitLogRequest;

import java.util.List;
import java.util.UUID;

public interface HabitLogService {
    HabitLog createHabitLog(HabitLogRequest habitLogRequest);
    List<HabitLog> getHabitLogsByHabitId(UUID habitId);
}
