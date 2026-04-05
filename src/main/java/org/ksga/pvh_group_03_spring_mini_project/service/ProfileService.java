package org.ksga.pvh_group_03_spring_mini_project.service;

import org.ksga.pvh_group_03_spring_mini_project.model.request.ProfileRequest;
import org.ksga.pvh_group_03_spring_mini_project.model.response.AppUserResponse;

public interface ProfileService {
    AppUserResponse getUserProfile();

    AppUserResponse updateProfile(ProfileRequest profileRequest);

    void deleteProfile();
}
