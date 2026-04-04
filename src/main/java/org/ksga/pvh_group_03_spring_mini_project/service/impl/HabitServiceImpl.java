package org.ksga.pvh_group_03_spring_mini_project.service.impl;

import lombok.RequiredArgsConstructor;
import org.ksga.pvh_group_03_spring_mini_project.exception.NotFoundException;
import org.ksga.pvh_group_03_spring_mini_project.helper.AuthUtils;
import org.ksga.pvh_group_03_spring_mini_project.model.entity.Habit;
import org.ksga.pvh_group_03_spring_mini_project.model.request.HabitRequest;
import org.ksga.pvh_group_03_spring_mini_project.repository.AppUserRepository;
import org.ksga.pvh_group_03_spring_mini_project.repository.HabitRepository;
import org.ksga.pvh_group_03_spring_mini_project.service.HabitService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HabitServiceImpl implements HabitService {
    private final HabitRepository habitRepository;
    private final AppUserRepository appUserRepository;
    private final ModelMapper modelMapper;
    private final AuthUtils authUtils;

    @Override
    public List<Habit> getAllHabits(Integer page, Integer size) {
       UUID userUUID = authUtils.getCurrentUserIdentifier();

        int offset = size * (page - 1);

        return habitRepository.getAllHabits(offset, size, userUUID);
    }

    @Override
    public Habit createHabit(HabitRequest request) {
        UUID appUserId = authUtils.getCurrentUserIdentifier();

        return habitRepository.createHabit(request, appUserId);
    }

    @Override
    public Habit getHabitById(UUID habitId) {
        UUID appUserId = authUtils.getCurrentUserIdentifier();
        Habit habit = habitRepository.getHabitById(habitId, appUserId);

        if (habit == null) {
            throw new NotFoundException("No habit found with ID " + habitId + ".");
        }

        if (appUserId == null) {
            throw new NotFoundException("No user found with this habit.");
        }
        return habit;
    }

    @Override
    public void deleteHabitById(UUID habitId) {
        UUID appUserId = authUtils.getCurrentUserIdentifier();
        Habit habit = habitRepository.getHabitById(habitId, appUserId);

        if (habit == null) {
            throw new NotFoundException("No habit found with ID " + habitId + "!");
        }

        habitRepository.deleteHabitById(habitId, appUserId);
    }

    @Override
    public Habit updateHabitById(UUID habitId, HabitRequest request) {
        UUID appUserId = authUtils.getCurrentUserIdentifier();
        Habit habit = habitRepository.getHabitById(habitId, appUserId);

        if (habit == null) {
            throw new NotFoundException("No habit found with given ID.");
        }

        habitRepository.updateHabitById(habitId, request, appUserId);

        return habitRepository.getHabitById(habitId, appUserId);
    }
}















