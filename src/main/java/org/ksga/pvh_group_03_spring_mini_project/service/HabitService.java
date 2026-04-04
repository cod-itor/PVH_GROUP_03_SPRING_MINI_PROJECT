package org.ksga.pvh_group_03_spring_mini_project.service;


import org.ksga.pvh_group_03_spring_mini_project.model.entity.Habit;
import org.ksga.pvh_group_03_spring_mini_project.model.request.HabitRequest;

import java.util.List;
import java.util.UUID;

public interface HabitService {

    List<Habit> getAllHabits(Integer page, Integer size);

    Habit createHabit(HabitRequest request);

    Habit getHabitById(UUID habitId);

    void deleteHabitById(UUID habitId);

    Habit updateHabitById(UUID habitId, HabitRequest request);
}