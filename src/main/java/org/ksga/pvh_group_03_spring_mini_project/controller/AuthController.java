package org.ksga.pvh_group_03_spring_mini_project.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ksga.pvh_group_03_spring_mini_project.model.request.AppUserRequest;
import org.ksga.pvh_group_03_spring_mini_project.model.request.AuthRequest;
import org.ksga.pvh_group_03_spring_mini_project.model.response.ApiResponse;
import org.ksga.pvh_group_03_spring_mini_project.model.response.AppUserResponse;
import org.ksga.pvh_group_03_spring_mini_project.model.response.TokenResponse;
import org.ksga.pvh_group_03_spring_mini_project.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auths")
public class AuthController {

    private final AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AppUserResponse>> registerUser(@RequestBody @Valid AppUserRequest appUserRequest) {
        AppUserResponse appUserResponse = authService.register(appUserRequest);
        ApiResponse<AppUserResponse> response = ApiResponse.<AppUserResponse>builder()
                .success(true)
                .message("User registered successfully! Please verify your email to complete the registration.")
                .status(HttpStatus.CREATED)
                .payload(appUserResponse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TokenResponse>> registerUser(@RequestBody @Valid AuthRequest authRequest) {
        TokenResponse tokenResponse = authService.login(authRequest);
        ApiResponse<TokenResponse> response = ApiResponse.<TokenResponse>builder()
                .success(true)
                .message("User login successfully!")
                .status(HttpStatus.OK)
                .payload(tokenResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("verify")
    public ResponseEntity<ApiResponse<?>> verifyOtp(@RequestParam String email,@RequestParam String otpCode) {
        authService.verifyUser(email, otpCode);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .success(true)
                .message("Email successfully verified! You can now log in.")
                .status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/resend")
    public ResponseEntity<ApiResponse<?>> resendOtp(@RequestParam String email) {
        authService.resendOTP(email);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .success(true)
                .message("Verification OTP successfully resend to your email.")
                .status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}