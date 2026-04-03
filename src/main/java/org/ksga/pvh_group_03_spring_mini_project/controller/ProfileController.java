package org.ksga.pvh_group_03_spring_mini_project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.ksga.pvh_group_03_spring_mini_project.model.response.ApiResponse;
import org.ksga.pvh_group_03_spring_mini_project.model.response.AppUserResponse;
import org.ksga.pvh_group_03_spring_mini_project.service.ProfileService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profiles")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class ProfileController {
    private final ProfileService profileService;
    private final ModelMapper modelMapper;

    @Operation(summary = "Get user profile",description = "Fetches the details of the currently authenticated user.")
    @GetMapping
    public ResponseEntity<ApiResponse<AppUserResponse>> getUserProfile() {
        AppUserResponse user = profileService.getUserProfile();

        ApiResponse<AppUserResponse> response = ApiResponse.<AppUserResponse>builder()
                .success(true)
                .message("Success")
                .status(HttpStatus.OK)
                .payload(user)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
