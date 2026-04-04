package org.ksga.pvh_group_03_spring_mini_project.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ksga.pvh_group_03_spring_mini_project.exception.NotFoundException;
import org.ksga.pvh_group_03_spring_mini_project.helper.AuthUtils;
import org.ksga.pvh_group_03_spring_mini_project.model.request.ProfileRequest;
import org.ksga.pvh_group_03_spring_mini_project.model.response.AppUserResponse;
import org.ksga.pvh_group_03_spring_mini_project.repository.AppUserRepository;
import org.ksga.pvh_group_03_spring_mini_project.repository.ProfileRepository;
import org.ksga.pvh_group_03_spring_mini_project.service.ProfileService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final AuthUtils authUtils;
    private final AppUserRepository appUserRepository;

    @Override
    public AppUserResponse getUserProfile() {
        UUID appUserId = authUtils.getCurrentUserIdentifier();
        AppUserResponse appUser = profileRepository.getUserProfile(appUserId);
        if (appUser == null) {
            throw new NotFoundException("User not found!");
        } else{
            return appUser;
        }
    }

    @Override
    public AppUserResponse updateProfile(ProfileRequest profileRequest) {
        UUID appUserId = authUtils.getCurrentUserIdentifier();
        AppUserResponse appUser = profileRepository.updateProfile(appUserId, profileRequest);

        if (appUser == null) {
            throw new NotFoundException("User not found!");
        } else{
            return appUser;
        }
    }

    @Override
    public void deleteProfile() {
        UUID appUserId = authUtils.getCurrentUserIdentifier();
        profileRepository.deleteProfile(appUserId);
    }
}
