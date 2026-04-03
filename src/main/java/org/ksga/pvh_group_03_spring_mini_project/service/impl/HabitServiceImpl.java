package org.ksga.pvh_group_03_spring_mini_project.service.impl;

import lombok.RequiredArgsConstructor;
import org.ksga.pvh_group_03_spring_mini_project.helper.AuthUtils;
import org.ksga.pvh_group_03_spring_mini_project.repository.AppUserRepository;
import org.ksga.pvh_group_03_spring_mini_project.repository.HabitRepository;
import org.ksga.pvh_group_03_spring_mini_project.service.HabitService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HabitServiceImpl implements HabitService {
    private final HabitRepository habitRepository;
    private final AppUserRepository appUserRepository;
    private final ModelMapper modelMapper;
    private final AuthUtils authUtils;
}