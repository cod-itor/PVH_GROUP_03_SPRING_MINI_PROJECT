package org.ksga.pvh_group_03_spring_mini_project.service;


import org.ksga.pvh_group_03_spring_mini_project.model.request.AppUserRequest;
import org.ksga.pvh_group_03_spring_mini_project.model.request.AuthRequest;
import org.ksga.pvh_group_03_spring_mini_project.model.response.AppUserResponse;
import org.ksga.pvh_group_03_spring_mini_project.model.response.TokenResponse;

public interface AuthService {
    AppUserResponse register(AppUserRequest appUserRequest) ;
    TokenResponse login(AuthRequest authRequest) ;
    void verifyUser(String email, String otpCode) ;
    void resendOTP(String key) ;
}
